package com.example.expensestracker.service;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import com.example.expensestracker.dao.entity.UserEntity;
import com.example.expensestracker.dao.repository.ExpenseRepository;
import com.example.expensestracker.dto.ExpenseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    public void testAddExpense() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("john_doe");

        ExpenseEntity expense = new ExpenseEntity();
        expense.setCategory("Food");
        expense.setDescription("Dinner");
        expense.setAmount(BigDecimal.valueOf(50.00));

        when(expenseRepository.save(any(ExpenseEntity.class))).thenReturn(expense);

        // Act
        ExpenseDTO result = expenseService.addExpense(expense, user);

        // Assert
        assertNotNull(result);
        assertEquals("Food", result.getCategory());
        assertEquals("Dinner", result.getDescription());
        assertEquals(BigDecimal.valueOf(50.00), result.getAmount());
        verify(expenseRepository, times(1)).save(any(ExpenseEntity.class));
    }

    @Test
    public void testGetExpenseById() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("john_doe");

        ExpenseEntity expense = new ExpenseEntity();
        expense.setId(1L);
        expense.setCategory("Food");
        expense.setDescription("Dinner");
        expense.setAmount(BigDecimal.valueOf(50.00));
        expense.setUser(user);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        // Act
        Optional<ExpenseDTO> result = expenseService.getExpenseById(1L, user);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Food", result.get().getCategory());
        verify(expenseRepository, times(1)).findById(1L);
    }
}