package org.example.restlibraryapp.controller;



import org.example.restlibraryapp.entity.Author;
import org.example.restlibraryapp.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for accessing endpoints related to AUTHORS
 */
@RestController
@RequestMapping("/library/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // get author
    @GetMapping("/{author_id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("author_id") long author_id) {
        authorService.getAuthorById(author_id);
        return new ResponseEntity<>(authorService.getAuthorById(author_id), HttpStatus.OK);
    }

    // create author
    @PostMapping()
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        authorService.createAuthor(author);
        return new  ResponseEntity<>(author, HttpStatus.CREATED);
    }

    // update author
    @PutMapping("/{author_id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long author_id, @RequestBody Author author) {
        authorService.updateAuthor(author_id, author);
        return new  ResponseEntity<>(author, HttpStatus.OK);
    }

    // delete author
    @DeleteMapping("/{author_id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long author_id) throws Exception {
        authorService.deleteAuthor(author_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    // add book to author
    @PostMapping("/{author_id}/add-book/{book_id}")
    public ResponseEntity<Author> addBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        authorService.addBookToAuthor(author_id, book_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    // remove book from author
    @DeleteMapping("/{author_id}/remove-book/{book_id}")
    public ResponseEntity<Author> removeBook(@PathVariable Long author_id, @PathVariable Long book_id) {
        authorService.removeBookFromAuthor(author_id, book_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
