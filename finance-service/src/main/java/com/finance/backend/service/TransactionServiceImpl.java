package com.finance.backend.service;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.finance.backend.dto.CategoryTrendDTO;
import com.finance.backend.dto.MonthlyTrendDTO;
import com.finance.backend.dto.YearlyTrendDTO;
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
	
	@Override
	public List<MonthlyTrendDTO> getMonthlyTrends(LocalDate start, LocalDate end) {
	    List<Object[]> results = transactionRepository.getMonthlyTrends(start, end);
        
        java.util.Map<String, MonthlyTrendDTO> trendMap = results.stream().map(obj -> {
            BigDecimal income = new BigDecimal(obj[2].toString());
            BigDecimal expense = new BigDecimal(obj[3].toString());
            return new MonthlyTrendDTO(
                ((Number) obj[0]).intValue(),
                ((Number) obj[1]).intValue(),
                income,
                expense,
                income.subtract(expense)
            );
        }).collect(Collectors.toMap(
            dto -> dto.getYear() + "-" + dto.getMonth(),
            dto -> dto
        ));

        List<MonthlyTrendDTO> fullTrends = new java.util.ArrayList<>();
        java.time.YearMonth currentMonth = java.time.YearMonth.from(start);
        java.time.YearMonth endMonth = java.time.YearMonth.from(end);

        while (!currentMonth.isAfter(endMonth)) {
            String key = currentMonth.getYear() + "-" + currentMonth.getMonthValue();
            if (trendMap.containsKey(key)) {
                fullTrends.add(trendMap.get(key));
            } else {
                fullTrends.add(new MonthlyTrendDTO(
                    currentMonth.getYear(),
                    currentMonth.getMonthValue(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
                ));
            }
            currentMonth = currentMonth.plusMonths(1);
        }
        
        return fullTrends;
	}

	@Override
	public List<YearlyTrendDTO> getYearlyTrends(LocalDate start, LocalDate end) {
	    List<Object[]> results = transactionRepository.getYearlyTrends(start, end);
        return results.stream().map(obj -> {
            BigDecimal income = new BigDecimal(obj[1].toString());
            BigDecimal expense = new BigDecimal(obj[2].toString());
            return new YearlyTrendDTO(
                ((Number) obj[0]).intValue(),
                income,
                expense,
                income.subtract(expense)
            );
        }).collect(Collectors.toList());
	}

	@Override
	public List<CategoryTrendDTO> getCategoryTrends(LocalDate start, LocalDate end) {
	    List<Object[]> results = transactionRepository.getCategoryTrends(start, end);
        return results.stream().map(obj -> new CategoryTrendDTO(
                (String) obj[0],
                TransactionType.valueOf(obj[1].toString()),
                new BigDecimal(obj[2].toString())
        )).collect(Collectors.toList());
	}

}
