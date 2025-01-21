package com.task.library.management.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.RequestEntity.RequestCatalog;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.CatalogService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
	
	// Dependency Injection
	@Autowired
	CatalogService_Impl catalogService_Impl;

	 //add new catalog
	 @PostMapping("/add_catalog")
	 public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody RequestCatalog requestCatalog) {
	 	DefaultResponse defaultResponse = catalogService_Impl.save(requestCatalog);
	     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	 }
	 
	//add new catalog
	@GetMapping("/get_catalog_category/{tmpcategory}")
	public ResponseEntity<Map<String, Object>> getCatalogByCategory(@Valid @PathVariable("tmpcategory") String category) {
		 DefaultResponse defaultResponse = catalogService_Impl.findCatalogByCategory(category);
		    return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		 }
	
	//add new catalog
	@PostMapping("/add_book_catalog/{tmpCatalogId}/{tmpBookId}")
	public ResponseEntity<Map<String, Object>> addBookToCatalog(@Valid @PathVariable("tmpCatalogId") int catalogId,
			@PathVariable("tmpBookId") int bookId) {
	DefaultResponse defaultResponse = catalogService_Impl.addBookToCatalog(catalogId, bookId);
		return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	}
	
	//add new catalog
		@GetMapping("/all_categories")
		public ResponseEntity<Map<String, Object>> getCateforyList() {
		DefaultResponse defaultResponse = catalogService_Impl.findAllCategoris();
			return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		}

}
