package com.task.library.management.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roll_id")
	private Integer rollId;
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonIgnore
	private Set<UserAccount> userAccounts;
}
