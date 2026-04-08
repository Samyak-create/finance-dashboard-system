package com.finance.backend.service;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.finance.backend.dto.CategoryTrendDTO;
import com.finance.backend.dto.MonthlyTrendDTO;
import com.finance.backend.dto.YearlyTrendDTO;
import com.finance.backend.entities.Transaction;
import com.finance.backend.enums.TransactionType;

public interface TransactionService {

	 Transaction addTransaction(Transaction transaction);
	
	 List<Transaction> getAllTransactions();
	
	 Transaction updateTransaction(Integer id,Transaction updated);
	
	 void deleteTransaction(Integer id);
	
	 BigDecimal getTotalIncome() ;
	
	 BigDecimal getTotalExpense();
	
	 BigDecimal getNetBalance();
	
	Page<Transaction> getTransactions(int page,int size);
	
	List<Transaction> getTransactionsByType(TransactionType type,int page,int size);

	List<Transaction> getTransactionsByDate(LocalDate start, LocalDate end);
	
	List<Transaction> getTransactionsByType(TransactionType type);
	
	List<MonthlyTrendDTO> getMonthlyTrends(LocalDate startDate, LocalDate endDate);
	
	List<YearlyTrendDTO> getYearlyTrends(LocalDate startDate, LocalDate endDate);
	
	List<CategoryTrendDTO> getCategoryTrends(LocalDate startDate, LocalDate endDate);
}
