package com.task.library.management.RequestEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestCatalog {

	private Integer catalogId;
	
	private String category;
	
    private List<RequestBook> books;	
}
