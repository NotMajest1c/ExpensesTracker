package com.example.expensestracker.controller;

import com.example.expensestracker.dao.entity.UserEntity;
import com.example.expensestracker.service.ReportService;
import com.example.expensestracker.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Dictionary;

@RestController
@RequestMapping("/report/expenses")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    private UserEntity getAuthenticatedUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public Dictionary<String, BigDecimal> getAllExpenses(Authentication authentication) {
        UserEntity user = getAuthenticatedUser(authentication);
        return reportService.getExpenseReport(user);

    }

}