package com.finance.backend.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.backend.entities.Transaction;
import com.finance.backend.enums.TransactionType;
import com.finance.backend.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	
	@Autowired
	private TransactionService transactionService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public Transaction addTransaction(@RequestBody Transaction transaction) {
		
		return transactionService.addTransaction(transaction);
	}
	
	
	
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Transaction updateTransactions(@PathVariable Integer id,@RequestBody Transaction t) {
    	return transactionService.updateTransaction(id, t);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Integer id) {
    	transactionService.deleteTransaction(id);
    }
    
    //To get total income
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/income")
    public BigDecimal getTotalIncome() {
    	return transactionService.getTotalIncome();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/expense")
    public BigDecimal getTotalExpense() {
    	return transactionService.getTotalExpense();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/summary/net")
    public BigDecimal getNetBalance() {
    	return transactionService.getNetBalance();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/filter")
    public Object getTransactionByType(
            @RequestParam TransactionType type,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && size != null) {
            return transactionService.getTransactionsByType(type, page, size);
        }

        return transactionService.getTransactionsByType(type);
    }
    
   
     
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/date")
    public List<Transaction> getByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return transactionService.getTransactionsByDate(start, end);
    }
    
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public Object getTransactions(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) TransactionType type) {

        //  Pagination + Filtering
        if (page != null && size != null && type != null) {
            return transactionService.getTransactionsByType(type, page, size);
        }

        //  Only Pagination
        if (page != null && size != null) {
            return transactionService.getTransactions(page, size);
        }

        //  Only Filter
        if (type != null) {
            return transactionService.getTransactionsByType(type);
        }

        //  Default → all data
        return transactionService.getAllTransactions();
    }
}
