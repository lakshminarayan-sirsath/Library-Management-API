package com.task.library.management.RequestEntity;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestUserAccount {

	private Integer accountId;

	private String userName;

	private String password;

	private LocalDate lastLogin; // @@@@@@@@@@@@@@@@

	private String status; // (e.g., Active, Locked)

	private Set<RequestRole> roles; // (e.g., Member, Admin)
	
	private RequestMember member; // it can be null, until it will become a member.
}
