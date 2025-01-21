package com.task.library.management.RollBasedAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.task.library.management.Entity.UserAccount;
import com.task.library.management.JpaRepo.UserAccountRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	// Dependency Injection
	@Autowired
	UserAccountRepo userAccountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserAccount userAccount = userAccountRepo.findByUsername(username);
		
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new CustomUserDetails(userAccount);
        
	}

}
