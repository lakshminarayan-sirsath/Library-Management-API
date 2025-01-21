package com.task.library.management.JpaRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.Catalog;


public interface CatalogRepo extends JpaRepository<Catalog, Integer>{
	
	@Query("SELECT c From Catalog c WHERE c.category = ?1")
	public Catalog findBy_Category(String category);
	
	@Query("SELECT c.category From Catalog c")
	public List<String> getAllCategoryList();

}
