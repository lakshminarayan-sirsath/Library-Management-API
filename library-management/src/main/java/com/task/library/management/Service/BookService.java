package com.task.library.management.Service;

import java.util.List;

import com.task.library.management.Entity.Book;
import com.task.library.management.RequestEntity.RequestBook;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseBook;

public interface BookService {

	DefaultResponse findAll();
	
	DefaultResponse findById(int bookId);
	
	DefaultResponse save(RequestBook requestBook);
	
	DefaultResponse update(RequestBook requestBook);
	
	DefaultResponse deleteById(int bookId);
	
	DefaultResponse changBookStatus(int bookId, String status);
	
	// Pagination
	DefaultResponse findAllBooks(int pageNum, int pageSize);
	
	// Search by title and filter using entity manager
	DefaultResponse searchBooksByTitle(String keyword, String filterType);
	
	// -------------------------------UserAccount-DTO-Methods----------------------------------------
	
	//Request
	Book requestBook_To_Book(RequestBook requestBook );
				
	//Response
	ResponseBook book_To_ResponseBook(Book book);
					
	//Request List
	List<Book> list_requestBook_To_Book (List<RequestBook> requestBooks);
					
	//Response List
	List<ResponseBook> list_book_To_ResponseBook(List<Book> books);
}
