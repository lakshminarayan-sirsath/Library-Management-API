package com.task.library.management.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.library.management.RequestEntity.RequestMember;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.Service_Impl.MemberService_Impl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	// Dependency Injection
	@Autowired
	MemberService_Impl memberService_Impl;

	// become member
    @PostMapping("/join_membership")
    public ResponseEntity<Map<String, Object>> newMember(@Valid @RequestBody RequestMember requestMember) {
    	DefaultResponse defaultResponse = memberService_Impl.joinMembership(requestMember);
        return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
    }
    
  // Get all user accounts
  @GetMapping("/select_all")
  public ResponseEntity<Map<String, Object>> findAll() {
      DefaultResponse defaultResponse = memberService_Impl.findAll();
      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
  }

  // Get student by ID
  @GetMapping("/select_by_id/{theId}")
  public ResponseEntity<Map<String, Object>> findById(@PathVariable("theId") int accountId) {
      DefaultResponse defaultResponse = memberService_Impl.findById(accountId);
      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
  }
  
//  // Update existing student
//  @PutMapping("/update_user_account")
//  public ResponseEntity<Map<String, Object>> updatStudent(@Valid @RequestBody RequestUserAccount requestUserAccount) {
//  	DefaultResponse defaultResponse = userAccountService_Impl.update(requestUserAccount);
//      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
//  }

  // Delete student by ID
  @DeleteMapping("/delete_by_id/{theId}")
  public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable("theId") int accountId) {
  	DefaultResponse defaultResponse = memberService_Impl.deleteById(accountId);
      return new ResponseEntity<>(defaultResponse.getDefultResponseDemo(), HttpStatus.OK); 
  }

}
