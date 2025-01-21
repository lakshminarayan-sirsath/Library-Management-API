package com.task.library.management.ResponseEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseCatalog {

	private Integer catalogId;
	
	private String category;
	
    private List<ResponseBookCatalog> books;
}
