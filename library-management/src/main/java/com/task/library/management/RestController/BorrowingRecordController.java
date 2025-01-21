package com.task.library.management.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.RequestEntity.RequestBorrowingRecord;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.BorrowingRecordService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/borrowing_record")
public class BorrowingRecordController {
	
	// Dependency Injection
	@Autowired
	BorrowingRecordService_Impl borrowingRecordService_Impl;
	
	 //borrow book
	 @PostMapping("/borrow_book")
	 public ResponseEntity<Map<String, Object>> bookBorrow(@Valid @RequestBody RequestBorrowingRecord requestBorrowingRecord) {
	 	DefaultResponse defaultResponse = borrowingRecordService_Impl.borrowBookSave(requestBorrowingRecord);
	     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	 }
	 
	//return book
		 @PutMapping("/return_book/{id}")
		 public ResponseEntity<Map<String, Object>> bookReturn(@PathVariable("id") int bookId) {
		 	DefaultResponse defaultResponse = borrowingRecordService_Impl.returnBookUpdate(bookId);
		     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		 }
		 
		//view borrowing record by member id
		 @GetMapping("/view_br_records/{id}")
		 public ResponseEntity<Map<String, Object>> viewBorrowRecords(@PathVariable("id") int memberId) {
		 	DefaultResponse defaultResponse = borrowingRecordService_Impl.viewBorrowingRecord(memberId);
		     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		 } 
}
