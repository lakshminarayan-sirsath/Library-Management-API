package com.task.library.management.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{


	@Query("SELECT r FROM Role r WHERE r.name = ?1")
	public Role getRoleByName(String roleName);
}
