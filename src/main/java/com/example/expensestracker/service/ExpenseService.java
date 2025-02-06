package com.example.expensestracker.service;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final List<ExpenseEntity> expenses = new ArrayList<>();
    private Long nextId = 1L; // Simulating ID generation

    public ExpenseEntity addExpense(ExpenseEntity expenseEntity) {
        expenseEntity.setId(nextId++);
        expenseEntity.setDate(LocalDateTime.now());
        expenses.add(expenseEntity);
        return expenseEntity;
    }

    public List<ExpenseEntity> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public Optional<ExpenseEntity> getExpenseById(Long id) {
        return expenses.stream().filter(expense -> expense.getId().equals(id)).findFirst();
    }

    public Optional<ExpenseEntity> updateExpense(Long id, ExpenseEntity updatedExpense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(id)) {
                updatedExpense.setId(id); // Keep the original ID
                updatedExpense.setDate(LocalDateTime.now());
                expenses.set(i, updatedExpense);
                return Optional.of(updatedExpense);
            }
        }
        return Optional.empty();
    }

    public boolean deleteExpense(Long id) {
        return expenses.removeIf(expense -> expense.getId().equals(id));
    }
}
