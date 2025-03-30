package org.example.restlibraryapp.controller;

import org.example.restlibraryapp.entity.Book;
import org.example.restlibraryapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for accessing endpoints related to BOOKS
 */

@RestController
@RequestMapping("/library/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // add book
    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return new  ResponseEntity<>(book, HttpStatus.CREATED);
    }


    // retrieve book
    @GetMapping("/{book_id}")
    public Book getBook(@PathVariable Long book_id) {
        return bookService.getBookById(book_id);
    }

    // view all books
    @GetMapping()
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }


    // update book details
    @PutMapping("/{book_id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long book_id, @RequestBody Book book) {
        bookService.updateBook(book_id, book);
        return new  ResponseEntity<>(book, HttpStatus.OK);
    }

    // delete book
    @DeleteMapping("/{book_id}")
    public ResponseEntity<Void> deleteLibraryMember(@PathVariable Long book_id) throws Exception {
        bookService.deleteBookById(book_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }


}
