package org.example.restlibraryapp.service;

import org.example.restlibraryapp.entity.Author;
import org.example.restlibraryapp.entity.Book;
import org.example.restlibraryapp.repository.AuthorRepo;
import org.example.restlibraryapp.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {


    private AuthorRepo authorRepo;
    private BookRepo bookRepo;

    public AuthorService(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    // read all authors

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    // read one author
    public Author getAuthorById(Long authorId) {
        return authorRepo.findById(authorId).orElse(null);
    }

    // create author
    public void createAuthor(Author author) {
        authorRepo.save(author);
    }

    // update author

    public void updateAuthor(Long authorId, Author author) {
        Author existingAuthor = authorRepo.findById(authorId).orElse(null);
        if (existingAuthor != null) {
            existingAuthor.setName(author.getName());
            existingAuthor.setBiography(author.getBiography());
        }
        authorRepo.save(existingAuthor);
    }

    // delete author
    public void deleteAuthor(Long authorId) throws Exception {
        // find author
        Author authorToDelete = authorRepo.findById(authorId).orElse(null);

        // check if author has books
        if (authorToDelete.getBooks() != null) {
            authorRepo.delete(authorToDelete);
        } else {
            throw new Exception("This author has books. They cannot be removed from database");
        }



    }

    // attach book to author
    public void addBookToAuthor(Long authorId, Long  bookId) {
        Author author = authorRepo.findById(authorId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (author != null && book != null) {
            author.addBook(book);
        }
        authorRepo.save(author);
    }

    // detach book from author
    public void removeBookFromAuthor(Long authorId, Long  bookId) {
        Author author = authorRepo.findById(authorId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);
        if (author != null && book != null) {
            author.removeBook(book);
        }
        authorRepo.save(author);
    }
}
