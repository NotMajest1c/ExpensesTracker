package com.example.expensestracker.service;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import com.example.expensestracker.dao.entity.UserEntity;
import com.example.expensestracker.dao.repository.ExpenseRepository;
import com.example.expensestracker.dto.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    // Convert ExpenseEntity to ExpenseDTO
    private ExpenseDTO convertToDTO(ExpenseEntity expense) {
        return new ExpenseDTO(
                expense.getId(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                expense.getUser().getUsername() // Only include the username
        );
    }

    public ExpenseDTO addExpense(ExpenseEntity expense, UserEntity user) {
        expense.setUser(user);
        expense.setDate(LocalDate.now());
        ExpenseEntity savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    public List<ExpenseDTO> getAllExpenses(UserEntity user) {
        return expenseRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ExpenseDTO> getExpenseById(Long id, UserEntity user) {
        return expenseRepository.findById(id)
                .filter(expense -> expense.getUser().equals(user))
                .map(this::convertToDTO);
    }

    public Optional<ExpenseDTO> updateExpense(Long id, ExpenseEntity updatedExpense, UserEntity user) {
        return expenseRepository.findById(id)
                .filter(expense -> expense.getUser().equals(user))
                .map(existingExpense -> {
                    existingExpense.setCategory(updatedExpense.getCategory());
                    existingExpense.setDescription(updatedExpense.getDescription());
                    existingExpense.setAmount(updatedExpense.getAmount());
                    existingExpense.setDate(LocalDate.now());
                    ExpenseEntity savedExpense = expenseRepository.save(existingExpense);
                    return convertToDTO(savedExpense);
                });
    }

    public boolean deleteExpense(Long id, UserEntity user) {
        return expenseRepository.findById(id)
                .filter(expense -> expense.getUser().equals(user))
                .map(expense -> {
                    expenseRepository.delete(expense);
                    return true;
                }).orElse(false);
    }

    public Page<ExpenseDTO> getExpensesByDateRange(UserEntity user, LocalDate start, LocalDate end, Pageable pageable) {
        return expenseRepository.findByUserAndDateBetween(user, start, end, pageable)
                .map(this::convertToDTO);
    }

    public Page<ExpenseDTO> getExpensesByCategory(UserEntity user, String category, Pageable pageable) {
        return expenseRepository.findByUserAndCategory(user, category, pageable)
                .map(this::convertToDTO);
    }
}