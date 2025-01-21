package com.task.library.management.Service_Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.Book;
import com.task.library.management.Entity.Catalog;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JpaRepo.BookRepo;
import com.task.library.management.JpaRepo.CatalogRepo;
import com.task.library.management.RequestEntity.RequestCatalog;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseCatalog;
import com.task.library.management.Service.CatalogService;

import jakarta.transaction.Transactional;

@Service
public class CatalogService_Impl implements CatalogService {
	// Dependency Injection
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CatalogRepo catalogRepo;
	
	@Autowired
	BookRepo bookRepo;

	@Transactional
	@Override
	public DefaultResponse save(RequestCatalog requestCatalog) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Catalog catalog = this.requestCatalog_To_Catalog(requestCatalog);
		
		Catalog dbCatalog = catalogRepo.findBy_Category(catalog.getCategory());
		if(dbCatalog != null) {
			map.put("status: ", false);
			map.put("message: ", "Catalog already exist (check by category)!!.");
			throw new CustomException(map);
		}
		
		// adding list of books as per category
		List<Book> books = bookRepo.findBookByCategory(catalog.getCategory());
		if(books != null) {
			catalog.setBooks(books);
		}
		else {
			catalog.setBooks(new ArrayList<Book>());
		}
		
		for(Book tmpBook: books) {
			tmpBook.setCatalog(catalog);
		}
		
		Catalog savedCatalog = catalogRepo.save(catalog);
		
		if(savedCatalog != null) {
			map.put("status: ", true);
			map.put("mesage: ", "Catalog saved successfully");
		    return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Catalog not saved, Something wrong!!.");
			
			throw new CustomException(map);
		}
	}
	
	@Override
	public DefaultResponse findCatalogByCategory(String category) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Catalog catalog = catalogRepo.findBy_Category(category);
		
		ResponseCatalog responseCatalog = this.Catalog_To_ResponseCatalog(catalog);
		
		if(catalog != null) {
			map.put("status: ", true);
			map.put("num of books: ", catalog.getBooks().size());
			map.put("mesage: ", "Catalog fetched successfully");
			map.put("catalog: ", responseCatalog);
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Somethig wrong or no data available in catalog table.");
			
			throw new CustomException(map);
		}
	}

	@Override
	public DefaultResponse addBookToCatalog(int catalogId, int bookId) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Catalog catalog = new Catalog();
		Book book = new Book();
		
		Optional<Catalog> catalogOptional = catalogRepo.findById(catalogId);
		if(catalogOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Invalid catalog id.");
			throw new CustomException(map);
		}
		else {
			catalog = catalogOptional.get();
		}
		
		Optional<Book> bookOptional = bookRepo.findById(bookId);
		if(bookOptional.isEmpty()) {
			map.put("status: ", false);
			map.put("message: ", "Invalid book id.");
			throw new CustomException(map);
		}
		else {
			book = bookOptional.get();
		}
		
		catalog.getBooks().add(book); // adding book to catalog
		book.setCatalog(catalog); // adding catalog to book
		
		Catalog savedCatalog = catalogRepo.save(catalog);
		
		if(savedCatalog != null) {
			map.put("status: ", false);
			map.put("message: ", "book added to catalog succefully.");
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Something Wrong, book not added to catalog.");
			throw new CustomException(map);
		}
	}
	
	@Override
	public DefaultResponse findAllCategoris() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		List<String> categories = catalogRepo.getAllCategoryList();
		
		List<String> responseList = new ArrayList<>();
		
		responseList = categories;
		
		if(categories != null) {
			map.put("status: ", true);
			map.put("num of categories: ", categories.size());
			map.put("mesage: ", "List of categories fetched successfully");
			map.put("catalogories: ", responseList);
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Something Wrong, or no categories are available.");
			throw new CustomException(map);
		}
		
	}

	
	// ----------------------------Catalog-DTO-methods----------------------------------------

	@Override
	public Catalog requestCatalog_To_Catalog(RequestCatalog requestCatalog) {
		Catalog catalog = modelMapper.map(requestCatalog, Catalog.class);
		return catalog;
	}

	@Override
	public ResponseCatalog Catalog_To_ResponseCatalog(Catalog catalog) {
		ResponseCatalog responseCatalog = modelMapper.map(catalog, ResponseCatalog.class);
		return responseCatalog;
	}

	@Override
	public List<Catalog> list_requestCatalog_To_Catalog(List<RequestCatalog> requestCatalogs) {
		List<Catalog> catalogs = new ArrayList<>();
		
		for(RequestCatalog tmpRequestCatalog: requestCatalogs) {
			Catalog catalog = modelMapper.map(tmpRequestCatalog, Catalog.class);
			catalogs.add(catalog);
		}
		return catalogs;
	}

	@Override
	public List<ResponseCatalog> list_Catalog_To_ResponseCatalog(List<Catalog> catalogs) {
		List<ResponseCatalog> responseCatalogs = new ArrayList<>();
		
		for(Catalog tmpCatalog: catalogs) {
			ResponseCatalog responseCatalog = modelMapper.map(tmpCatalog, ResponseCatalog.class);
			responseCatalogs.add(responseCatalog);
		}
		return responseCatalogs;
	}


	
}
