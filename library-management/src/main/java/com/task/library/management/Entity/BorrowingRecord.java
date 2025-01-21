package com.task.library.management.Entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Integer recordId;
	
	@Column(name = "issue_date")
	private LocalDate issueDate;
	
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(name = "return_date")
	private LocalDate returnDate;
	
//	@Column(name = "fine_amount")
//	private Integer fineAmount;
	
	@Column(name = "status")
	private String status; // (e.g., Borrowed, Returned, Overdue)
	
	// BookID (Foreign Key)
	// deleting borrowing record will not delete book.
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}) //, fetch = FetchType.LAZY
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;
	
	// MemberID (Foreign Key)
	// deleting borrowing record will not delete member.
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}) //, fetch = FetchType.LAZY
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

}
