package com.task.library.management.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.task.library.management.Entity.UserAccount;
import com.task.library.management.RequestEntity.RequestUserAccount;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseUserAccount;

public interface UserAccountService {

	DefaultResponse findAll();
	
	DefaultResponse findById(int accountId);
	
	DefaultResponse save(RequestUserAccount requestUserAccount);
	
	DefaultResponse update(RequestUserAccount requestUserAccount);
	
	DefaultResponse deleteById(int accountId);
	
	// -------------------------------UserAccount-DTO-Methods----------------------------------------
	
	//Request
	UserAccount requestUserAccount_To_UserAccount(RequestUserAccount requestUserAccount );
				
	//Response
	ResponseUserAccount userAccount_To_ResponseUserAccount(UserAccount userAccount);
					
	//Request List
	List<UserAccount> list_RequestUserAccount_To_UserAccount(List<RequestUserAccount> requestUserAccounts);
					
	//Response List
	List<ResponseUserAccount> list_UserAccount_To_ResponseUserAccount(List<UserAccount> userAccounts);
	
}
