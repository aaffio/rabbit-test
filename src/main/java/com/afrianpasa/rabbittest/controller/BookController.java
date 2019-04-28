package com.afrianpasa.rabbittest.controller;

import com.afrianpasa.rabbittest.domain.Book;
import com.afrianpasa.rabbittest.domain.request.BookIdRequest;
import com.afrianpasa.rabbittest.rabbitmq.publisher.BookPublisher;
import com.afrianpasa.rabbittest.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("book")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookPublisher bookPublisher;

    @GetMapping
    public ResponseEntity getBooks(@RequestParam(name = "id", required = false) Integer id,
                                   @RequestParam(name = "title", required = false) String title,
                                   @RequestParam(name = "author", required = false) String author,
                                   @RequestParam(name = "genre", required = false) String genre) {
        return bookService.getBooks(id, title, author, genre);
    }

    @PutMapping
    public ResponseEntity putBook(@RequestBody Book book) {
        return bookService.putBook(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookById(@PathVariable("id") Integer id) {
        return bookService.findById(id);
    }

    @GetMapping("/async/{id}")
    public ResponseEntity getBookByIdAsync(@PathVariable("id") Integer id) {
        bookPublisher.getBookById(new BookIdRequest(id));
        return ResponseEntity.ok().build();
    }


}
