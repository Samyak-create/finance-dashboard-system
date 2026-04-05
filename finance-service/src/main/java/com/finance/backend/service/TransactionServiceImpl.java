package com.finance.backend.service;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.backend.entities.Transaction;
import com.finance.backend.enums.TransactionType;
import com.finance.backend.repository.TransactionRepository;

import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest;

@Service
public class TransactionServiceImpl  implements TransactionService{

	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	
	@Override
	public Transaction addTransaction(Transaction transaction) {
		
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getAllTransactions() {
	
		return transactionRepository.findAll();
	}

	@Override
	public Transaction updateTransaction(Integer id, Transaction updated) {
		 Transaction transaction=transactionRepository.findById(id)
				 .orElseThrow(()-> new RuntimeException ("Transaction not found with id "+id));
		transaction.setAmount(updated.getAmount());
		transaction.setType(updated.getType());;
		transaction.setCategory(updated.getCategory());
		transaction.setDate(updated.getDate());;
		transaction.setDescription(updated.getDescription());
		return transactionRepository.save(transaction);
	}

	@Override
	public void deleteTransaction(Integer id) {
		transactionRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalIncome() {
		
		return transactionRepository.findAll().stream()
				.filter(t->t.getType()==TransactionType.INCOME)
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public BigDecimal getTotalExpense() {
		
		return transactionRepository.findAll().stream()
				.filter(t->t.getType()==TransactionType.EXPENSE)
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public BigDecimal getNetBalance() {
		return getTotalIncome().subtract(getTotalExpense());
	}
	
	@Override
	public Page<Transaction> getTransactions(int page,int size){
		return transactionRepository.findAll(PageRequest.of(page,size));
	}
	
	@Override
	public List<Transaction> getTransactionsByType(TransactionType type,int page,int size){
		return transactionRepository.findByType(type,PageRequest.of(page, size));
	}
    
	@Override
	public List<Transaction> getTransactionsByDate(LocalDate start, LocalDate end) {
		if (start.isAfter(end)) {
	        throw new RuntimeException("Start date cannot be after end date");
	    }
	    return transactionRepository.findByDateBetween(start, end);
	}
	
	@Override
	public List<Transaction> getTransactionsByType(TransactionType type) {
	    return transactionRepository.findByType(type);
	}
	

}
