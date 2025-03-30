package org.example.restlibraryapp.service;

import org.example.restlibraryapp.entity.Book;
import org.example.restlibraryapp.entity.BorrowRecord;
import org.example.restlibraryapp.entity.LibraryMember;
import org.example.restlibraryapp.repository.BookRepo;
import org.example.restlibraryapp.repository.BorrowRecordRepo;
import org.example.restlibraryapp.repository.LibraryMemberRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class BorrowRecordService {
    private BookRepo bookRepo;
    private BorrowRecordRepo borrowRecordRepo;
    private LibraryMemberRepo libraryMemberRepo;

    public BorrowRecordService(BookRepo bookRepo, BorrowRecordRepo borrowRecordRepo, LibraryMemberRepo libraryMemberRepo) {
        this.bookRepo = bookRepo;
        this.borrowRecordRepo = borrowRecordRepo;
        this.libraryMemberRepo = libraryMemberRepo;
    }

    // create borrow record when book is borrowed
    public void createBorrowRecord(Long bookId, Long memberId) {
        LocalDate borrowDate = LocalDate.now();
        Book book = bookRepo.findById(bookId).orElse(null);
        LibraryMember libraryMember = libraryMemberRepo.findById(memberId).orElse(null);
        BorrowRecord borrowRecord = new BorrowRecord(borrowDate);

        // attach borrow record to member
        if (libraryMember != null) {
            borrowRecord.setLibraryMember(libraryMember);

        }
        // attach borrow record to book
        if (book != null) {
            borrowRecord.setBook(book);
        }

        borrowRecordRepo.save(borrowRecord);

    }

    // update borrow record when book is returned
    public void removeBorrowRecord(Long bookId, Long memberId) {
        LocalDate returnDate = LocalDate.now();
        LibraryMember libraryMember = libraryMemberRepo.findById(memberId).orElse(null);
        Set<BorrowRecord> borrowedBooksBySelectedMember = libraryMember.getBorrowedBooks();
        Book  book = bookRepo.findById(bookId).orElse(null);
        BorrowRecord borrowRecordToRemove = null;

        // find the correct borrowedRecord
        for (BorrowRecord borrowRecord : borrowedBooksBySelectedMember) {
            if (borrowRecord.getBook().getId().equals(bookId)) {
                borrowRecordToRemove = borrowRecord;
            }
        }

        if (borrowRecordToRemove !=null)
        {

            // remove book
            borrowRecordToRemove.setBook(null);
            // remove member

            borrowRecordToRemove.setLibraryMember(null);

            borrowRecordToRemove.setReturnDate(returnDate);

        }

        borrowRecordRepo.save(borrowRecordToRemove);


    }

    public void getAll() {
        borrowRecordRepo.findAll();
    }

    public void getByMemberId(Long memberId) {
        borrowRecordRepo.findBorrowRecordByLibraryMember_Id(memberId);
    }

    public void getByBookId(Long bookId) {
        borrowRecordRepo.findBorrowRecordByBook_Id(bookId);
    }
}
