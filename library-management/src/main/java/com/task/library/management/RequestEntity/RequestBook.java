package com.task.library.management.RequestEntity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestBook {

	private Integer bookId;

	private String title;

	private String author;

	private String publisher;

	private String category;

	private String edition;

	private String shelf;

	private String status; // (e.g., Available, Issued, Reserved)

	private String language;

	private LocalDate dateAdded;
}
