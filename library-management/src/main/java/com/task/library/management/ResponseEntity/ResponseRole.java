package com.task.library.management.ResponseEntity;

import java.util.Set;

import com.task.library.management.Entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseRole {

	private Integer rollId;

	private String name; 

	private Set<ResponseUserAccount> userAccounts;
}
