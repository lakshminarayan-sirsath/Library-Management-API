package com.task.library.management.RequestEntity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestMember {
	
	private Integer memberId;
	
	private String name;
	
	private String email;
	
	private Integer contactNo;
	
	private String membershipType;

	private String address;
	
	private LocalDate dateOfMembership;
	
	private String status; // (e.g., Active, Suspended)

}
