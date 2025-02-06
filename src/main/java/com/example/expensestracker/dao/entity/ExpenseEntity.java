package com.example.expensestracker.dao.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {
    private Long id;

    private Integer amount;
    private String description;

    private LocalDateTime date;

    private String category;
}
