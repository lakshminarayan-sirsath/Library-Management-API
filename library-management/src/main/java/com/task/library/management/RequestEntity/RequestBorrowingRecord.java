package com.task.library.management.RequestEntity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestBorrowingRecord {

	private Integer recordId;

	private LocalDate issueDate;
	
	private LocalDate dueDate;

	private LocalDate returnDate;

//	private Integer fineAmount;

	private String status; // (e.g., Borrowed, Returned, Overdue)
	
	private Integer idBook;
	
	private Integer idMember;
	
//	private RequestBook requestBook;
	
//	private RequestMember requestMember;

}
