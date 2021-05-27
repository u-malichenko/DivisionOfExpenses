package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuElementEvent {
    private String eventName;
    private Calendar eventDateTime;
    private int expenseCount;
    private List<String> eventUserList;
    private BigDecimal totalEventSum;
}
