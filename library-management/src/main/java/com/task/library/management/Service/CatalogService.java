package com.task.library.management.Service;

import java.util.List;

import com.task.library.management.Entity.Book;
import com.task.library.management.Entity.Catalog;
import com.task.library.management.RequestEntity.RequestBook;
import com.task.library.management.RequestEntity.RequestCatalog;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseBook;
import com.task.library.management.ResponseEntity.ResponseCatalog;

public interface CatalogService {
	
	DefaultResponse save(RequestCatalog requestCatalog);
	
	DefaultResponse findCatalogByCategory(String category);
	
	DefaultResponse addBookToCatalog(int catalogId, int bookId);
	
	DefaultResponse findAllCategoris();
	
	// ----------------------------Catalog-DTO-methods----------------------------------------
	
		//Request
		Catalog requestCatalog_To_Catalog(RequestCatalog requestCatalog );
					
		//Response
		ResponseCatalog Catalog_To_ResponseCatalog(Catalog catalog);
						
		//Request List
		List<Catalog> list_requestCatalog_To_Catalog (List<RequestCatalog> requestCatalogs);
						
		//Response List
		List<ResponseCatalog> list_Catalog_To_ResponseCatalog(List<Catalog> catalogs);
}
