package org.example.restlibraryapp.repository;

import org.example.restlibraryapp.entity.Book;
import org.example.restlibraryapp.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepo extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findBorrowRecordByLibraryMember_Id(Long libraryMemberId);

    List<BorrowRecord> findBorrowRecordByBook(Book book);

    List<BorrowRecord> findBorrowRecordByBook_Id(Long bookId);
}
