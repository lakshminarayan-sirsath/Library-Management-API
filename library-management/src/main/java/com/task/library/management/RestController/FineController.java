package com.task.library.management.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.FineService_Impl;

@RestController
@RequestMapping("/fine")
public class FineController {
	
	// Dependency Injection
	@Autowired
	FineService_Impl fineService_Impl;
	
	// pay fine
		 @PutMapping("/pay_fine/{id}")
		 public ResponseEntity<Map<String, Object>> payFinEntity(@PathVariable("id") int fineId) {
		 DefaultResponse defaultResponse = fineService_Impl.payFine(fineId);
		     return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
		 }

}
