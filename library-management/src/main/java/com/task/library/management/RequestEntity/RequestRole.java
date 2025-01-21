package com.task.library.management.RequestEntity;

import java.util.Set;

import com.task.library.management.Entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestRole {

	private Integer rollId;

	private String name; 

	private Set<RequestUserAccount> userAccounts;
}
