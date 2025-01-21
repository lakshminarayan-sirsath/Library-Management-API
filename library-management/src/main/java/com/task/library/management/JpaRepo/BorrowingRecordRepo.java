package com.task.library.management.JpaRepo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.BorrowingRecord;

public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Integer>{
	
	@Query("SELECT br FROM BorrowingRecord br WHERE br.book.id = ?1 AND br.status = 'issued'")
	public BorrowingRecord getBorrowedBookRecordToReturnBook(int bookId);
	
	@Query("SELECT br FROM BorrowingRecord br WHERE br.dueDate < ?1 AND br.status = 'issued'")
	List<BorrowingRecord> findOverdueBooks(LocalDate currentDate);


}
