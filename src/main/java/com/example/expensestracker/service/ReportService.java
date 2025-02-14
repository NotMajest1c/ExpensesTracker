package com.example.expensestracker.service;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import com.example.expensestracker.dao.entity.UserEntity;
import com.example.expensestracker.dao.repository.ExpenseRepository;
import com.example.expensestracker.dto.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
@Service
@RequiredArgsConstructor
public class ReportService {

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
    public Dictionary<String, BigDecimal> getExpenseReport(UserEntity user) {
        List<ExpenseDTO> expenseDTOList = expenseRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .toList();
        ArrayList<String> Categories = new ArrayList<>();
        Dictionary<String, BigDecimal> UserExpensesCategories = new Hashtable<>();
        for (ExpenseDTO expense : expenseDTOList) {
            if (!Categories.contains(expense.getCategory())) {
                UserExpensesCategories.put(expense.getCategory(), expense.getAmount());
                Categories.add(expense.getCategory());
            }
            else{
                UserExpensesCategories.put(expense.getCategory(), expense.getAmount().add(UserExpensesCategories.get(expense.getCategory())));
            }
        }
        return UserExpensesCategories;
    }

}