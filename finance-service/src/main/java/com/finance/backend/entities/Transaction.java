package com.finance.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.finance.backend.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="transactions")
@Data
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="Amount is required")
	@DecimalMin(value="0.0",inclusive=false,message="Amount must be positive")
	@Column(nullable=false)
	private BigDecimal amount;
	
	@NotNull(message="Transaction type is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable=false  )
	private TransactionType type;
   
	@NotBlank(message="Category is required")
	private String category;
	
	
	@NotNull(message="Date is required")
	@Column(nullable=false)
	private LocalDate date;
	
	private String description; 
}
