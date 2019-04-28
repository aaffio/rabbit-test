package com.afrianpasa.rabbittest.service;

import com.afrianpasa.rabbittest.domain.Book;
import com.afrianpasa.rabbittest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<List<Book>> getBooks(Integer id, String title, String author, String genre) {
        Book book = new Book(id, title, author, genre);
        return ResponseEntity.ok().body(bookRepository.findAll(Example.of(book)));
    }

    public ResponseEntity<Book> putBook(Book book) {
        if (bookRepository.exists(Example.of(book))) {
            return ResponseEntity.ok().body(updateBook(book));
        } else {
            return ResponseEntity.ok(createBook(book));
        }
    }

    private Book createBook(Book book) {
        return bookRepository.save(book);
    }

    private Book updateBook(Book book) {
        Book oldBook = bookRepository.findById(book.getId()).get();
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setGenres(book.getGenres());
        return bookRepository.save(oldBook);
    }

    public ResponseEntity<Book> findById(Integer id) {
        if (bookRepository.existsById(id)) {
            return ResponseEntity.ok().body(bookRepository.findById(id).get());
        }
        return ResponseEntity.notFound().build();
    }
}
