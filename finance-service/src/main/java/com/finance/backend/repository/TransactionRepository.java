package com.finance.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.backend.entities.Transaction;
import com.finance.backend.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
	List<Transaction> findByType(TransactionType type);
	List<Transaction> findByType(TransactionType type,Pageable pageable);
	List<Transaction> findByDateBetween(LocalDate start,LocalDate end);
}
