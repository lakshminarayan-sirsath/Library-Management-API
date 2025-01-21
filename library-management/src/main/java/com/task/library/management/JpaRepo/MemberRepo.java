package com.task.library.management.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.library.management.Entity.Member;

public interface MemberRepo extends JpaRepository<Member, Integer> {

	@Query("SELECT m FROM Member m WHERE m.email = ?1")
	Member findMemberByEmail(String email);
}
