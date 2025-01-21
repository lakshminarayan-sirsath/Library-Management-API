package com.task.library.management.Service_Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.Book;
import com.task.library.management.Entity.BorrowingRecord;
import com.task.library.management.Entity.Member;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JpaRepo.BookRepo;
import com.task.library.management.JpaRepo.BorrowingRecordRepo;
import com.task.library.management.JpaRepo.MemberRepo;
import com.task.library.management.RequestEntity.RequestBorrowingRecord;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseBorrowingRecord;
import com.task.library.management.Service.BorrowingRecordService;

import jakarta.transaction.Transactional;

@Service
public class BorrowingRecordService_Impl implements BorrowingRecordService {
	
	// Dependency Injection
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	BorrowingRecordRepo borrowingRecordRepo;
	
	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Transactional
	@Override
	public DefaultResponse borrowBookSave(RequestBorrowingRecord requestBorrowingRecord) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		Book book = new Book();
		Member member = new Member();
		
		BorrowingRecord borrowingRecord = this.requestBorrowingRecord_To_BorrowingBookRecord(requestBorrowingRecord);
	
		Optional<Book> bookOptional = bookRepo.findById(requestBorrowingRecord.getIdBook());
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
			
			if(!"AVAILABLE".equalsIgnoreCase(book.getStatus())) {
				map.put("status: ", false);
				map.put("message: ", "Book status, not available.");	
				throw new CustomException(map);
			}
			
			borrowingRecord.setBook(book);
			
			book.getBorrowingRecords().add(borrowingRecord); // bi-dir
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Invalid book id.");	
			throw new CustomException(map);
		}
		
		Optional<Member> memberOptional = memberRepo.findById(requestBorrowingRecord.getIdMember()); 
		if(memberOptional.isPresent()) {

			member = memberOptional.get();
			borrowingRecord.setMember(member);
			
			member.getBorrowingRecords().add(borrowingRecord);// bi_dir
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Invalid member id.");	
			throw new CustomException(map);
		}
		
		borrowingRecord.getBook().setStatus("ISSUED"); // borrowing book
		BorrowingRecord dbBorrowingRecord = borrowingRecordRepo.save(borrowingRecord);
		
		if(dbBorrowingRecord != null) {
			map.put("status: ", true);
			map.put("message: ", "borrowed book successfully.");	
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Something wrong, book not borrowed.");	
			throw new CustomException(map);
		}
	}
	
	
	@Override
	public DefaultResponse returnBookUpdate(int bookId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		Book book = new Book();
		
		Optional<Book> bookOptional = bookRepo.findById(bookId);
		
		if(bookOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Invalid book id.");	
			throw new CustomException(map);
		}
		
		BorrowingRecord borrowingRecord = borrowingRecordRepo.getBorrowedBookRecordToReturnBook(bookId);
		if(borrowingRecord == null) {
			map.put("status: ", false);
			map.put("message: ", "No borrowing record (issued).");	
			throw new CustomException(map);
		}
		
		borrowingRecord.getBook().setStatus("AVAILABLE");
		borrowingRecord.setStatus("returned");
		
		BorrowingRecord dbBorrowingRecord = borrowingRecordRepo.save(borrowingRecord);
		
		if(dbBorrowingRecord != null) {
			map.put("status: ", true);
			map.put("message: ", "returned book successfully.");	
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Something wrong, book not returned.");	
			throw new CustomException(map);
		}
	}
	
	@Override
	public DefaultResponse viewBorrowingRecord(int memberId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		Member member = new Member();	
		
		Optional<Member> memberOptional = memberRepo.findById(memberId);
		if(memberOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Invalid member id.");	
			throw new CustomException(map);
		}
		member = memberOptional.get();
		
		List<BorrowingRecord> borrowingRecords = member.getBorrowingRecords();
		
		if(borrowingRecords == null) {
			map.put("status: ", false);
			map.put("message: ", "Something wrong or no borrowing record.");	
			throw new CustomException(map);
		}
		else {
			List<ResponseBorrowingRecord> responseBorrowingRecords = this.list_BorrowingRecord_To_ResponseBorrowingRecord(borrowingRecords);
			map.put("status: ", true);
			map.put("message: ", "list of borrowing record of membername: "+member.getName());
			map.put("num of records: ", responseBorrowingRecords.size());
			map.put("borrowing records: ", responseBorrowingRecords);
			return new DefaultResponse(map);
		}

	}

	// -------------------------------BorrowingRecords-DTO-Methods---------------------------------------

	@Override
	public BorrowingRecord requestBorrowingRecord_To_BorrowingBookRecord(
			RequestBorrowingRecord requestBorrowingRecord) {
		BorrowingRecord borrowingRecord = modelMapper.map(requestBorrowingRecord, BorrowingRecord.class);
		return borrowingRecord;
	}

	@Override
	public ResponseBorrowingRecord BorrowingRecord_To_ResponseBorrowingRecord(BorrowingRecord borrowingRecord) {
		ResponseBorrowingRecord responseBorrowingRecord = modelMapper.map(borrowingRecord, ResponseBorrowingRecord.class);
		return responseBorrowingRecord;
	}

	@Override
	public List<BorrowingRecord> list_requestBorrowingRecord_To_BorrowingRecord(
			List<RequestBorrowingRecord> requestBorrowingRecords) {
		List<BorrowingRecord> borrowingRecords = new ArrayList<>();	
		for(RequestBorrowingRecord tmpRequestBorrowingRecord: requestBorrowingRecords) {
			BorrowingRecord borrowingRecord = modelMapper.map(tmpRequestBorrowingRecord, BorrowingRecord.class);
			borrowingRecords.add(borrowingRecord);
		}
		return borrowingRecords;
	}

	@Override
	public List<ResponseBorrowingRecord> list_BorrowingRecord_To_ResponseBorrowingRecord(
			List<BorrowingRecord> borrowingRecords) {
		List<ResponseBorrowingRecord> responseBorrowingRecords = new ArrayList<>();
		for(BorrowingRecord tmpBorrowingRecord: borrowingRecords) {
			ResponseBorrowingRecord responseBorrowingRecord = modelMapper.map(tmpBorrowingRecord, ResponseBorrowingRecord.class);
			responseBorrowingRecords.add(responseBorrowingRecord);
		}
		return responseBorrowingRecords;
	}





}
