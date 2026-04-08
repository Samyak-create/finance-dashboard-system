package com.finance.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finance.backend.entities.Transaction;
import com.finance.backend.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
	List<Transaction> findByType(TransactionType type);
	List<Transaction> findByType(TransactionType type,Pageable pageable);
	List<Transaction> findByDateBetween(LocalDate start,LocalDate end);
	
	@Query("SELECT YEAR(t.date), " +
		   "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), " +
		   "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) " +
		   "FROM Transaction t " +
		   "WHERE t.date BETWEEN :startDate AND :endDate " +
		   "GROUP BY YEAR(t.date) " +
		   "ORDER BY YEAR(t.date) ASC")
	List<Object[]> getYearlyTrends(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT YEAR(t.date), MONTH(t.date), " +
		   "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), " +
		   "SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) " +
		   "FROM Transaction t " +
		   "WHERE t.date BETWEEN :startDate AND :endDate " +
		   "GROUP BY YEAR(t.date), MONTH(t.date) " +
		   "ORDER BY YEAR(t.date) ASC, MONTH(t.date) ASC")
	List<Object[]> getMonthlyTrends(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT t.category, t.type, SUM(t.amount) " +
		   "FROM Transaction t " +
		   "WHERE t.date BETWEEN :startDate AND :endDate " +
		   "GROUP BY t.category, t.type " +
		   "ORDER BY SUM(t.amount) DESC")
	List<Object[]> getCategoryTrends(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
