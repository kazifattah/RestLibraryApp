package org.example.restlibraryapp.controller;


import org.example.restlibraryapp.entity.BorrowRecord;
import org.example.restlibraryapp.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for accessing endpoints related to BORROWING and RETURNING BOOKS
 */

@RestController
@RequestMapping("/library/borrowed-books/")
public class BorrowRecordController {

    BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    // get all borrow records
    @GetMapping()
    public ResponseEntity<Void> getAllBorrowedBooks() {
        borrowRecordService.getAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // get borrow record by customer
    @GetMapping("/{member_id}")
    public ResponseEntity<Void> getBorrowedBookByMemberId(@PathVariable Long member_id) {
        borrowRecordService.getByMemberId(member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // get borrow record by book
    @GetMapping("/{book_Id}")
    public ResponseEntity<Void> getBorrowedBookByBookId(@PathVariable Long book_Id) {
        borrowRecordService.getByBookId(book_Id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // borrow books (create borrow record)
    @PostMapping("/borrow-book/{member_id}")
    public ResponseEntity<Void> createNewRecord(@RequestParam Long book_id, @PathVariable Long member_id)  {
        borrowRecordService.createBorrowRecord(book_id,member_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    // return books (update borrow record)
    @DeleteMapping("/return-book/{member_id}")
    public ResponseEntity<Void> removeRecord(@RequestParam Long book_id, @PathVariable Long member_id)  {
        borrowRecordService.removeBorrowRecord(book_id,member_id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
