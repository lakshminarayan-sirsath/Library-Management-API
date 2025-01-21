package com.task.library.management.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.RequestEntity.RequestBook;
import com.task.library.management.RequestEntity.RequestMember;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.BookService_Impl;
import com.task.library.management.Service_Impl.MemberService_Impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
@Tag(name = "Book-API", description = "add-book, update-book, select-book ect..") // swagger controller name
public class BookController {

	  // Dependency Injection
	  @Autowired
	  BookService_Impl bookService_Impl;
    
	  // -------------------------------------Pagination------------------------------------------------
	  // Get all user books
	  @GetMapping("/select_all_pagination/pageNum-{num}/pageSize-{size}")
	  public ResponseEntity<Map<String, Object>> findAllUsingPagination(
			  @PathVariable(value = "num") Integer pageNum,
		        @PathVariable(value = "size") Integer pageSize) {
	      DefaultResponse defaultResponse = bookService_Impl.findAllBooks(pageNum, pageSize);
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	  
	// -------------------------------------Pagination--------------------------------------------------
	
	  
	  // Get all user books
	  @GetMapping("/select_all")
	  public ResponseEntity<Map<String, Object>> findAll() {
	      DefaultResponse defaultResponse = bookService_Impl.findAll();
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	  
	
	  // Get book by ID
	  @GetMapping("/select_by_id/{theId}")
	  public ResponseEntity<Map<String, Object>> findById(@PathVariable("theId") int bookId) {
	      DefaultResponse defaultResponse = bookService_Impl.findById(bookId);
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	  
	  //add new book
	 @PostMapping("/add_book")
	 public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody RequestBook requestBook) {
	 	DefaultResponse defaultResponse = bookService_Impl.save(requestBook);
	     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	 }
	  
	  // Update existing book
	  @PutMapping("/update_book")
	  public ResponseEntity<Map<String, Object>> updatStudent(@Valid @RequestBody RequestBook requestBook) {
	  	DefaultResponse defaultResponse = bookService_Impl.update(requestBook);
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	
	  // Delete student by ID
	  @DeleteMapping("/delete_by_id/{theId}")
	  public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable("theId") int bookId) {
	  	DefaultResponse defaultResponse = bookService_Impl.deleteById(bookId);
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	  
	  // change book status
	  @PutMapping("/change_book_status/{tmpBookId}/{tmpStatus}")
	  public ResponseEntity<Map<String, Object>> changeBookStatus(@PathVariable("tmpBookId") int bookId, @PathVariable("tmpStatus") String status) {
	  	DefaultResponse defaultResponse = bookService_Impl.changBookStatus(bookId, status);
	      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
	  }
	  

	  
	// Search book by title and filter using entity manager
	  @Operation(
		        summary = "Search books by title and filter",
		        description = "Filters: (starting, ending, contain, or exactly)"
		    )
	  @GetMapping("/search_by_title/keywords-{keyword}/search_type{type}")
		 public ResponseEntity<Map<String, Object>> viewBorrowRecords(@PathVariable("keyword") String searchKeyword, @PathVariable("type") String searchType) {
		 	DefaultResponse defaultResponse = bookService_Impl.searchBooksByTitle(searchKeyword, searchType);
		    return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		 } 
}
