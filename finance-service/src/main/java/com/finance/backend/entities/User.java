package com.finance.backend.entities;

import com.finance.backend.enums.Role;
import com.finance.backend.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Name cannot Be empty")
	@Column(nullable=false)
	private String name;
	
	@NotBlank(message="Email cannot be empty")
	@Email(message="Invalid email format")
	@Column(nullable=false,unique=true)
	private String email;
	
	@NotBlank(message="password cannot be empty")
	@Column(nullable=false)
	private String password;
	
	
	@NotNull(message="Role is Required")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@NotNull(message="Status is Required")
	@Enumerated(EnumType.STRING)
	private Status status;
	

}
