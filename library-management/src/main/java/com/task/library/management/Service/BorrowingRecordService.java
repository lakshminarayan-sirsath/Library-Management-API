package com.task.library.management.Service;

import java.util.List;

import com.task.library.management.Entity.BorrowingRecord;
import com.task.library.management.RequestEntity.RequestBorrowingRecord;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseBorrowingRecord;

public interface BorrowingRecordService {

//DefaultResponse findAll();
	
	DefaultResponse borrowBookSave(RequestBorrowingRecord requestBorrowingRecord); // borrow book
	
	DefaultResponse returnBookUpdate(int bookId); // return book;
	
	DefaultResponse viewBorrowingRecord(int memberID); // view borrowing record of member

	
	// -------------------------------BorrowingRecord-DTO-Methods----------------------------------------
	
	//Request
	BorrowingRecord requestBorrowingRecord_To_BorrowingBookRecord(RequestBorrowingRecord requestBorrowingRecord);
				
	//Response
	ResponseBorrowingRecord BorrowingRecord_To_ResponseBorrowingRecord(BorrowingRecord borrowingRecord);
					
	//Request List
	List<BorrowingRecord> list_requestBorrowingRecord_To_BorrowingRecord(List<RequestBorrowingRecord> requestBorrowingRecords);
					
	//Response List
	List<ResponseBorrowingRecord> list_BorrowingRecord_To_ResponseBorrowingRecord(List<BorrowingRecord> borrowingRecords);
}
