package com.example.expensestracker.dao.repository;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import com.example.expensestracker.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByUser(UserEntity user);
    Page<ExpenseEntity> findByUserAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<ExpenseEntity> findByUserAndCategory(UserEntity user, String category, Pageable pageable);
}
