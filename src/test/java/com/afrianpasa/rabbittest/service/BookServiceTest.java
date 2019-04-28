package com.afrianpasa.rabbittest.service;

import com.afrianpasa.rabbittest.RabbitTestApplicationTests;
import com.afrianpasa.rabbittest.domain.Book;
import com.afrianpasa.rabbittest.repository.BookRepository;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class BookServiceTest extends RabbitTestApplicationTests {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    Gson gson;

    @Before
    public void setUp() {
        this.gson = new Gson();
    }

    @After
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void createBook() {
        Book book = new Book(null, "Title test", "test author", "test genre");
        ResponseEntity responseEntity = bookService.putBook(book);
        assert 200 == responseEntity.getStatusCodeValue();
        Book bookResponse = (Book) responseEntity.getBody();
        assert "Title test".equals(bookResponse.getTitle());
    }

    @Test
    public void updateBook() {
        Book book = new Book(null, "Title test", "test author", "test genre");
        Book bookResponse = bookService.putBook(book).getBody();
        assert null != bookResponse;
        bookResponse.setAuthor("test author updated");
        ResponseEntity responseEntity = bookService.putBook(bookResponse);
        assert HttpStatus.OK.value() == responseEntity.getStatusCodeValue();
        Book bookResponse1 = (Book) responseEntity.getBody();
        assert bookResponse1 != null;
        assert "test author updated".equals(bookResponse1.getAuthor());
    }

    @Test
    public void getBooks() {
        initBook();
        List<Book> books = bookService.getBooks(null, "Title test", null, null).getBody();
        assertNotNull(books);
        assert 1 == books.size();
    }

    private void initBook() {
        Book book = new Book(null, "Title test", "test author", "test genre");
        bookRepository.save(book);
    }

    @Test
    public void findById() {
        initBook();
        List<Book> books = bookService.getBooks(null, "Title test", null, null).getBody();
        assert books != null;
        books.forEach(book -> {
            assertNotNull(book);
            ResponseEntity responseEntity = bookService.findById(book.getId());
            assert HttpStatus.OK.value() == responseEntity.getStatusCodeValue();
            Book bookResponse = (Book) responseEntity.getBody();
            assert bookResponse != null;
            assertEquals("Title test", bookResponse.getTitle());
        });
    }

    @Test
    public void findByIdNotFound() {
        ResponseEntity responseEntity = bookService.findById(999);
        assert HttpStatus.NOT_FOUND.value() == responseEntity.getStatusCodeValue();
    }
}