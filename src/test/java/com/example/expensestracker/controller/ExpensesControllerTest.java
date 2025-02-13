package com.example.expensestracker.controller;

import com.example.expensestracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ExpensesControllerTest {

    @Mock
    private ExpenseService expenseService;


    @InjectMocks
    private ExpensesController expensesController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllExpenses() throws Exception {
        // Arrange
        mockMvc = MockMvcBuilders.standaloneSetup(expensesController).build();
        when(expenseService.getAllExpenses(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/expenses")
                        .header("Authorization", "Bearer <JWT_TOKEN>"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}