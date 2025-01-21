package com.task.library.management.Service_Impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder.RedefinitionListenable.WithoutBatchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.Book;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JpaRepo.BookRepo;
import com.task.library.management.RequestEntity.RequestBook;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseBook;
import com.task.library.management.Service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//@Repository // Purpose: Indicates that the class is a Data Access Object (DAO) that interacts with the database.
@Service
public class BookService_Impl implements BookService{
	// roles
	private static final Set<String> BookStatus = Set.of("AVAILABLE", "UNAVAILABLE", "ISSUED", "RESERVED");

		// validate roles
		private void validateBookStatus(String status) {
				if (!BookStatus.contains(status.toUpperCase())) {
			        Map<String, Object> map = new HashMap<>();
			        map.put("status: ", false);
			        map.put("message: ", "Invalid status specified: " +status);
			        throw new CustomException(map);
			    }
		}
	
	// Dependency Injection
	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public DefaultResponse findAll() {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>(); // Using for Error response and Success Response
		
		List<Book> books = bookRepo.findAll();
		List<ResponseBook> responseBooks = this.list_book_To_ResponseBook(books);
		
		if(responseBooks != null) {
			map.put("status: ", true);
			map.put("num of books: ", map.size());
			map.put("mesage: ", "books fetched successfully");
			map.put("student-list: ", responseBooks);
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Somethig wrong or no data available in book table.");
			
			throw new CustomException(map);
		}
	}

	@Override
	public DefaultResponse findById(int bookId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		// Container for null handing, avoid NullPointerException.
		Optional<Book> bookOptional = bookRepo.findById(bookId);
			
		Book book;
		
		if(bookOptional.isPresent()) {
			book = bookOptional.get();
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Book not found!!, Invalid id.");
			
			throw new CustomException(map);
		}
			
		ResponseBook responseBook = this.book_To_ResponseBook(book);
	   	map.put("status: ", true);
		map.put("mesage: ", "UserAccount fetched successfully");
	    map.put("student: ", responseBook);
	    
	    return new DefaultResponse(map);
	}

	@Override
	public DefaultResponse save(RequestBook requestBook) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Book book = this.requestBook_To_Book(requestBook);
		
		Book dbBook = bookRepo.findBookByTitle(book.getTitle());
		if(dbBook != null) {
			map.put("status: ", false);
			map.put("message: ", "Book already exist (check by book title)!!.");
			
			throw new CustomException(map);
		}
		
		Book savedBook = bookRepo.save(book);
		
		if(savedBook != null) {
			map.put("status: ", true);
			map.put("mesage: ", "Book saved successfully");
		    return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Book not saved, Something wrong!!.");
			
			throw new CustomException(map);
		}
	
	}

	@Override
	public DefaultResponse update(RequestBook requestBook) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		Book book = this.requestBook_To_Book(requestBook);
		
		Optional<Book> bookOptional = bookRepo.findById(requestBook.getBookId());
		if(bookOptional.isPresent()) {
			
			Book updateBook = bookRepo.save(book); // update
		     
		    if(updateBook == null) {
				map.put("status: ", false);
				map.put("message: ", "Something wrong, Book not updated.");
					
				throw new CustomException(map);
		     }
		    else {
		    	map.put("status: ", true);
			    map.put("message: ", "Book updated successfuly.");
			    return new DefaultResponse(map);
		    }
		    
		}
		else {
			 map.put("status: ", false);
			 map.put("message: ", "Invalid id, Book can't updated..");
					
			 throw new CustomException(map);
		}
	}

	@Override
	public DefaultResponse deleteById(int bookId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Optional<Book> bookOptional = bookRepo.findById(bookId);
		if(bookOptional.isPresent()) {
			
			 bookRepo.deleteById(bookId);
			
			 map.put("status: ", true);
			 map.put("message: ", "Book deleted successfuly.");
	    	 return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Invalid id, Book can't deleted..");
					
			throw new CustomException(map);
		}
	}
	
	@Override
	public DefaultResponse changBookStatus(int bookId, String status) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		Book book = new Book();
			
			Optional<Book> bookOptional = bookRepo.findById(bookId);
			if(bookOptional.isPresent()) {
				
				// validate status
				this.validateBookStatus(status); // if not valid throw customException
				
				 book = bookOptional.get();
				 
				 book.setStatus(status.toUpperCase()); // changing book status
				 Book savedBook = bookRepo.save(book);
				 if(savedBook != null) {
					 map.put("status: ", true);
					 map.put("message: ", "Book status changed successfuly.");
			    	 return new DefaultResponse(map);
				 }
				 else {
					 map.put("status: ", false);
					 map.put("message: ", "Something wrong, Book status not changed.");
			    	 return new DefaultResponse(map);
				 }
				 
			}
			else {
				map.put("status: ", false);
				map.put("message: ", "Invalid id, Book status can't changed.");
						
				throw new CustomException(map);
			}
	}
	
	
	@Override
	public DefaultResponse findAllBooks(int pageNum, int pageSize) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			Pageable pageable = PageRequest.of(pageNum, pageSize);
			
			Page<Book> books = bookRepo.getAllBooks(pageable);
			if (books.isEmpty()) {
				map.put("status: ", true);
				map.put("message: ", "No data is avaible in this page.(Books: pageNum-"+pageNum+", pageSize-"+pageSize+")");
				return new DefaultResponse(map);
			} 
			
			  // Convert Page<Book> to Page<ResponseBook>
	        Page<ResponseBook> responseBooks = books.map(book -> modelMapper.map(book, ResponseBook.class));
	        
			map.put("status: ", true);
			map.put("message: ", "Books: pageNum-"+pageNum+", pageSize-"+pageSize);
			map.put("books: ", responseBooks);
			return new DefaultResponse(map);
		} catch (Exception e) {
			map.put("status: ", false);
			map.put("message: ", "Exception occures");
			map.put("exception: ", e.getMessage());
			throw new CustomException(map);
		}
		
	}
	
	
	@PersistenceContext // Purpose: Injects an instance of EntityManager to interact with the persistence context.
	private EntityManager entityManager;

    @Override
    public DefaultResponse searchBooksByTitle(String keyword, String filterType) {
    	Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
    	
        String queryString = "SELECT b FROM Book b WHERE ";
        switch (filterType.toLowerCase()) {
            case "starting":									 
                queryString += "b.title LIKE '"+keyword+"'%"; // We can fully remove one parameter by concatenating string with actual query. 
                keyword = keyword + "%";
                break;
            case "ending":
                queryString += "b.title LIKE :keyword";
                keyword = "%" + keyword;
                break;
            case "contain":
                queryString += "b.title LIKE :keyword";
                keyword = "%" + keyword + "%";
                break;
            case "exactly":
                queryString += "b.title = :keyword";
                break;
            default:
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
        }

        Query query = entityManager.createQuery(queryString);
        query.setParameter("keyword", keyword);
        
        List<Book> books = query.getResultList();
        
        Map<String, Object> response = new HashMap<>();
        
        if(books.isEmpty()) {
        	response.put("status: ", false);
        	response.put("message: ", "Zero(0) search result, book not found.");
        	return new DefaultResponse(response);
        }
        else {
        	response.put("status: ", true);
        	response.put("message: ", "Zero(0) search result, book not found.");
        	response.put("books: ", books);
        	return new DefaultResponse(response);
		}
    }

	
	
	// ---------------------------Book-DTO-Methods----------------------------------------

	@Override
	public Book requestBook_To_Book(RequestBook requestBook) {
		Book book = modelMapper.map(requestBook, Book.class);
		return book;
	}

	@Override
	public ResponseBook book_To_ResponseBook(Book book) {
		ResponseBook responseBook = modelMapper.map(book, ResponseBook.class);
		return responseBook;
	}

	@Override
	public List<Book> list_requestBook_To_Book(List<RequestBook> requestBooks) {
		List<Book> books = new ArrayList<>();
		
		for(RequestBook tmpRequestBook: requestBooks) {
			Book book = modelMapper.map(tmpRequestBook, Book.class);
			books.add(book);
		}
		return books;
	}

	@Override
	public List<ResponseBook> list_book_To_ResponseBook(List<Book> books) {

		List<ResponseBook> responseBooks = new ArrayList<>();
		
		for(Book tmpBook: books) {
			ResponseBook responseBook = modelMapper.map(tmpBook, ResponseBook.class);
			responseBooks.add(responseBook);
		}
		return responseBooks;
	}


	
}
