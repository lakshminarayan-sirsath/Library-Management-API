package com.task.library.management.Service_Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.library.management.DtoClass.AuthRequest;
import com.task.library.management.Entity.Role;
import com.task.library.management.Entity.UserAccount;
import com.task.library.management.GlobalException.CustomException;
import com.task.library.management.JWT_utility.JwtUtil;
import com.task.library.management.JpaRepo.RoleRepo;
import com.task.library.management.JpaRepo.UserAccountRepo;
import com.task.library.management.RequestEntity.RequestAssignRole;
import com.task.library.management.RequestEntity.RequestUserAccount;
import com.task.library.management.ResponseEntity.DefaultResponse;
import com.task.library.management.ResponseEntity.ResponseUserAccount;
import com.task.library.management.Service.UserService;

@Service
public class UserService_Impl implements UserService{
	
	// roles
	private static final Set<String> ALLOWED_ROLES = Set.of("MEMBER", "ADMIN", "VISITOR", "LIBRARIAN");

	// validate roles
	private void validateRole( Set<Role> roles) {
		for(Role tmpRole: roles) {
			if (!ALLOWED_ROLES.contains(tmpRole.getName().toUpperCase())) {
		        Map<String, Object> map = new HashMap<>();
		        map.put("status: ", false);
		        map.put("message: ", "Invalid role specified: " + tmpRole.getName());
		        throw new CustomException(map);
		    }
		}
	    
	}
	
	// Dependency Injection
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserAccountRepo userAccountRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	public DefaultResponse registerUser(RequestUserAccount requestUserAccount) {
		Map<String, Object> map = new HashMap<>();
		
		UserAccount userAccount = this.requestUserAccount_To_UserAccount(requestUserAccount);
		
		// add default role VISITOR if not specified
		if(userAccount.getRoles().isEmpty()) {
			Role role = new Role();
			role.setName("VISITOR");
			userAccount.getRoles().add(role);
		}
		
		this.validateRole(userAccount.getRoles()); // validating roles else throw custom exception
		
		// check user already exist or not
		if(userAccountRepo.findByUsername(userAccount.getUserName()) != null) {
			map.put("status: ", false);
	        map.put("message: ", "Error! user already exist.(check by username)");
	        throw new CustomException(map);
		}
		else {
			userAccount.setPassword(this.passwordEncoder.encode(userAccount.getPassword())); // encode password
		    UserAccount savedUser = userAccountRepo.save(userAccount);
		    if (savedUser == null) {
		        map.put("status: ", false);
		        map.put("message: ", "Error! user not registered.(not saved)");
		        throw new CustomException(map);
		    }

		    map.put("status: ", true);
		    map.put("message: ", "User registered successfully.");
		    return new DefaultResponse(map);
		}
	}

	@Override
	public DefaultResponse loginUser(AuthRequest authRequest) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> claims = new HashMap<>();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(
			          new UsernamePasswordAuthenticationToken(
			        		  authRequest.getUsername(),
			        		  authRequest.getPassword())
			          );

			String jwt = jwtUtil.generateToken(claims, authRequest.getUsername()); // claim is empty
			map.put("status: ", true);
			map.put("message: ", "login success.(valid user)");
			map.put("jwt token: ", jwt);
					
			return new DefaultResponse(map);
			
		} catch (BadCredentialsException e) {
			map.put("status: ", false);
			map.put("message: ", e.getMessage()+" (exception)");
			throw new CustomException(map);
		}
	
	}

	@Override
	public DefaultResponse logoutUser(RequestUserAccount requestUserAccount) {
		// TODO Auto-generated method stub
//		@Override
//		public DefaultResponse logoutUser(RequestUserAccount requestUserAccount) {
//		    Map<String, Object> map = new HashMap<>();
//		    
//		    try {
//		        // Clear the security context
//		        SecurityContextHolder.clearContext();
//
//		        map.put("status: ", true);
//		        map.put("message: ", "Logout successful.");
//		        return new DefaultResponse(map);
//		    } catch (Exception e) {
//		        map.put("status: ", false);
//		        map.put("message: ", "Error during logout: " + e.getMessage());
//		        throw new CustomException(map);
//		    }
//		}

		return null;
	}

	
	// for admin
	@Override
	public DefaultResponse findAll() {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>(); // Using for Error response and Success Response
		
		List<UserAccount> userAccounts = userAccountRepo.findAll();
		List<ResponseUserAccount> responseUserAccounts = this.list_UserAccount_To_ResponseUserAccount(userAccounts);
		
		if(responseUserAccounts != null) {
			map.put("status: ", true);
			map.put("num of users: ", map.size());
			map.put("mesage: ", "Users fetched successfully");
			map.put("student-list: ", responseUserAccounts);
			return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Somethig wrong or no data available in user_account table.");
			
			throw new CustomException(map);
		}
	}

	@Override
	public DefaultResponse findById(int accountId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		// Container for null handing, avoid NullPointerException.
		Optional<UserAccount> userAcResult = userAccountRepo.findById(accountId);
			
		UserAccount userAccount;
		
		if(userAcResult.isPresent()) {
			userAccount = userAcResult.get();
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "User-Account not found!!, Invalid id.");
			
			throw new CustomException(map);
		}
			
		ResponseUserAccount responseUserAccount = this.userAccount_To_ResponseUserAccount(userAccount);
	   	map.put("status: ", true);
		map.put("mesage: ", "UserAccount fetched successfully");
	    map.put("student: ", responseUserAccount);
	    
	    return new DefaultResponse(map);
	}

	@Override
	public DefaultResponse deleteById(int accountId) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		Optional<UserAccount> userAcResult = userAccountRepo.findById(accountId);
		if(userAcResult.isPresent()) {
			
			 userAccountRepo.deleteById(accountId);
			
			 map.put("status: ", true);
			 map.put("message: ", "User deleted successfuly.");
	    	 return new DefaultResponse(map);
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "Invalid id, User can not deleted..");
					
			throw new CustomException(map);
		}
	}
	
	
	@Override
	public DefaultResponse assignRole(RequestAssignRole requestAssignRole) {
		Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
		
		Map<String, Object> map = new HashMap<>();
		
		// Container for null handing, avoid NullPointerException.
		Optional<UserAccount> userOptional = userAccountRepo.findById(requestAssignRole.getUserId());
			
		UserAccount userAccount = new UserAccount();
		
		if(userOptional.isPresent()) {
			userAccount = userOptional.get();
		}
		else {
			map.put("status: ", false);
			map.put("message: ", "User-Account not found!!, Invalid id.");
			
			throw new CustomException(map);
		}
		
		if(!ALLOWED_ROLES.contains(requestAssignRole.getRole().toUpperCase())) {
			map.put("status: ", false);
			map.put("message: ", "Invalid role.");
			throw new CustomException(map);
		}
		
		if(userAccount.getRoles().contains(requestAssignRole.getRole())) {
			map.put("status: ", false);
			map.put("message: ", "User already contain this role: "+requestAssignRole.getRole());
			throw new CustomException(map);
		}
		
		Role role = roleRepo.getRoleByName(requestAssignRole.getRole());
		if(role == null) {
			role.setName(requestAssignRole.getRole());
			Set<UserAccount> users = new HashSet<>();
			users.add(userAccount);
			role.setUserAccounts(users);
			
			userAccount.getRoles().add(role);
		}
		else {
			role.getUserAccounts().add(userAccount);
			
			userAccount.getRoles().add(role);
		}

	 	UserAccount savedAccount = userAccountRepo.save(userAccount);
	 	if(savedAccount == null) {
	 		map.put("status: ", false);
			map.put("message: ", "Role not assiged");
			throw new CustomException(map);
	 	}
	 	else {
	 		map.put("status: ", true);
			map.put("message: ", "Role assiged successfully.");
			
			return new DefaultResponse(map);
	 	}
	    
	}
	// -------------------------------UserAccount-DTO-Methods----------------------------------------

		@Override
		public UserAccount requestUserAccount_To_UserAccount(RequestUserAccount requestUserAccount) {
			UserAccount userAccount = modelMapper.map(requestUserAccount, UserAccount.class);
			return userAccount;
		}

		@Override
		public ResponseUserAccount userAccount_To_ResponseUserAccount(UserAccount userAccount) {
			ResponseUserAccount responseUserAccount = modelMapper.map(userAccount, ResponseUserAccount.class);
			return responseUserAccount;
			
//			ResponseUserAccount responseUserAccount = modelMapper.map(userAccount, ResponseUserAccount.class);
////			
//			Set<ResponseRole> responseRoles = new HashSet<>();
//			for(Role tmpRole: userAccount.getRoles()) {
//				ResponseRole responseRole = modelMapper.map(tmpRole, ResponseRole.class);
//				
//				responseRoles.add(responseRole);
//			}
//			
//			responseUserAccount.setRoles(responseRoles);
//			return responseUserAccount;
		}
		
//		ResponseStudent responseStudent = this.modelMapper.map(theStudent, ResponseStudent.class);
//		List<String> courseTitleStrings = new ArrayList<>();
//		for(Course tmpCourse: theStudent.getCourses()) {
//			courseTitleStrings.add(tmpCourse.getCourseTitle());
//		}
//		responseStudent.setCourseTitlse(courseTitleStrings);
//		return responseStudent;

		@Override
		public List<UserAccount> list_RequestUserAccount_To_UserAccount(List<RequestUserAccount> requestUserAccounts) {
			List<UserAccount> userAccounts = new ArrayList<>(); // List of user accounts
			
			for(RequestUserAccount tmpRequestAc: requestUserAccounts) {
				UserAccount userAccount = modelMapper.map(tmpRequestAc, UserAccount.class);
				userAccounts.add(userAccount);
			}
			
			return userAccounts;
		}

		@Override
		public List<ResponseUserAccount> list_UserAccount_To_ResponseUserAccount(List<UserAccount> userAccounts) {
			List<ResponseUserAccount> responseUserAccounts = new ArrayList<>();
			
			for(UserAccount tmpUserAccount: userAccounts) {
				ResponseUserAccount responseUserAccount = modelMapper.map(tmpUserAccount, ResponseUserAccount.class);
				responseUserAccounts.add(responseUserAccount);
			}
			
			return responseUserAccounts;
		}


}
