package com.task.library.management.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.UserAccount;

public interface UserAccountRepo extends JpaRepository<UserAccount, Integer>{
	
	@Query("SELECT u FROM UserAccount u WHERE u.userName = ?1")
	public UserAccount findByUsername(String userName);
}
	
 
