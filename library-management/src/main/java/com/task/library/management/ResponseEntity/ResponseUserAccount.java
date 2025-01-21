package com.task.library.management.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

import com.task.library.management.RequestEntity.RequestMember;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserAccount {
	
	private Integer accountId;

	private String userName;

	private String password;

	private LocalDate lastLogin; // @@@@@@@@@@@@@@@@

	private String status; // (e.g., Active, Locked)

	private Set<ResponseRole> roles; // (e.g., Member, Admin)
	
	private RequestMember member; // it can be null, until it will become a member.
}
