package com.task.library.management.JpaRepo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {  // BookSearchService adding for book search implementation using EntitManager
	
	@Query("SELECT b FROM Book b WHERE b.title = ?1")
	public Book findBookByTitle(String bookTitle);
	
	@Query("SELECT b FROM Book b WHERE b.category = ?1")
	public List<Book> findBookByCategory(String category);
	
	// Pagination
	@Query("SELECT b FROM Book b")
	public Page<Book> getAllBooks(Pageable pageable);
	
}
