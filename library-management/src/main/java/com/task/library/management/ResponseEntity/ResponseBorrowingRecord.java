package com.task.library.management.ResponseEntity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBorrowingRecord {

	private Integer recordId;

	private LocalDate issueDate;
	
	private LocalDate dueDate;

	private LocalDate returnDate;

//	private Integer fineAmount;

	private String status; // (e.g., Borrowed, Returned, Overdue)

//	private ResponseBook responseBook;
	private Integer bookId;
	
	private Integer memberId;
	
//	private ResponseMember responseMember;
}
