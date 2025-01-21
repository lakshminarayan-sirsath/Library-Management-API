package com.task.library.management.Service;

import java.util.List;

import com.task.library.management.DtoClass.AuthRequest;
import com.task.library.management.Entity.UserAccount;
import com.task.library.management.RequestEntity.RequestAssignRole;
import com.task.library.management.RequestEntity.RequestUserAccount;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseUserAccount;

public interface UserService {
	
	// for all
	DefaultResponse registerUser(RequestUserAccount requestUserAccount);
	DefaultResponse loginUser(AuthRequest authRequest);
	DefaultResponse logoutUser(RequestUserAccount requestUserAccount);
	
	// for only Admin
	DefaultResponse findAll();
	
	DefaultResponse findById(int accountId);
	
	DefaultResponse deleteById(int accountId);
	
	DefaultResponse assignRole(RequestAssignRole requestAssignRole);

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
