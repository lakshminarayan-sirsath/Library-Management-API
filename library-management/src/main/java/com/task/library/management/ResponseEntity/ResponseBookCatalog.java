package com.task.library.management.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBookCatalog {

	private String title;
	
	private String shelf;

	private String status; // (e.g., Available, Issued, Reserved)
}
