package org.example.restlibraryapp.service;

import org.example.restlibraryapp.entity.Author;
import org.example.restlibraryapp.entity.Book;
import org.example.restlibraryapp.repository.AuthorRepo;
import org.example.restlibraryapp.repository.BookRepo;
import org.example.restlibraryapp.repository.BorrowRecordRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepo bookRepo;
    private AuthorRepo authorRepo;
    private BorrowRecordRepo borrowedRecordRepo;

    public BookService(BookRepo bookRepo, AuthorRepo authorRepo, BorrowRecordRepo borrowedRecordRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.borrowedRecordRepo = borrowedRecordRepo;
    }

    // get all books

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    // get one book
    public Book getBookById(Long bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    // get all books
    public List<Book> getBooks() {
       return bookRepo.findAll();
    }

    // create new book
    public void createBook(Book book) {
        bookRepo.save(book);
    }

    // update book info
    public void updateBook(Long bookId, Book book) {
        Book bookToUpdate = bookRepo.findById(bookId).orElse(null);
        if  (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setIsbn(book.getIsbn());
            bookToUpdate.setPublicationYear(book.getPublicationYear());
        }

        bookRepo.save(bookToUpdate);
    }

    // delete book
    public void deleteBookById(Long bookId) throws Exception {

        // find book
        Book  bookToDelete = bookRepo.findById(bookId).orElse(null);


        if (bookToDelete != null) {
            // check if book is borrowed
            if (bookToDelete.getBorrowRecords() == null) {

                // sever connection to authors
                if (bookToDelete.getAuthors() != null) {
                    for (Author author : bookToDelete.getAuthors()) {
                        author.removeBook(bookToDelete);
                    }
                }

                // then delete
                bookRepo.delete(bookToDelete);
            } else {
                throw new Exception("Book has been lent. Cannot be deleted from database.");
            }
        }




    }


}
