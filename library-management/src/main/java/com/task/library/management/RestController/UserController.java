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
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.DtoClass.AuthRequest;
import com.task.library.management.RequestEntity.RequestAssignRole;
import com.task.library.management.RequestEntity.RequestUserAccount;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.UserService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	// Dependency Injection
	@Autowired
	UserService_Impl userService_Impl;
	
	// register
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> newUserAccount(@Valid @RequestBody RequestUserAccount requestUserAccount) {
    	DefaultResponse defaultResponse = userService_Impl.registerUser(requestUserAccount);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }
	
    // login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signIn(@Valid @RequestBody AuthRequest authRequest) {
    	DefaultResponse defaultResponse = userService_Impl.loginUser(authRequest);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }
    
    
    // -------------------------------------for-admin-only-----------------------------------------------
    // Get all user accounts
    @GetMapping("/select_all")
    public ResponseEntity<Map<String, Object>> findAll() {
        DefaultResponse defaultResponse = userService_Impl.findAll();
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }

    // Get student by ID
    @GetMapping("/select_by_id/{theId}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("theId") int accountId) {
        DefaultResponse defaultResponse = userService_Impl.findById(accountId);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }

    // Delete student by ID
    @DeleteMapping("/delete_by_id/{theId}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable("theId") int accountId) {
    	DefaultResponse defaultResponse = userService_Impl.deleteById(accountId);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }
    
 // Delete student by ID
    @PutMapping("/add_role")
    public ResponseEntity<Map<String, Object>> deleteStudent(@RequestBody RequestAssignRole requestAssignRole) {
    	DefaultResponse defaultResponse = userService_Impl.assignRole(requestAssignRole);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }
    
}
