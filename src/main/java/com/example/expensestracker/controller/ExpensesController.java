package com.example.expensestracker.controller;

import com.example.expensestracker.dao.entity.ExpenseEntity;
import com.example.expensestracker.dao.entity.UserEntity;
import com.example.expensestracker.dto.ExpenseDTO;
import com.example.expensestracker.service.ExpenseService;
import com.example.expensestracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpenseService expenseService;
    private final UserService userService;

    private UserEntity getAuthenticatedUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ExpenseDTO addExpense(@RequestBody ExpenseEntity expenseEntity, Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        return expenseService.addExpense(expenseEntity, user);
    }

    @GetMapping
    public List<ExpenseDTO> getAllExpenses(Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        return expenseService.getAllExpenses(user);
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable Long id, Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        return expenseService.getExpenseById(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(@PathVariable Long id, @RequestBody ExpenseEntity updatedExpense, Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        return expenseService.updateExpense(id, updatedExpense, user)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id, Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        if (!expenseService.deleteExpense(id, user)) {
            throw new RuntimeException("Expense not found");
        }
    }

    @GetMapping("/filter")
    public Page<ExpenseDTO> getExpensesByFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        UserEntity user = getAuthenticatedUser(authentication);
        PageRequest pageRequest = PageRequest.of(page, size);

        if (category != null) {
            return expenseService.getExpensesByCategory(user, category, pageRequest);
        }
        return expenseService.getExpensesByDateRange(user, startDate, endDate, pageRequest);
    }
}