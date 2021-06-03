package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {

    private Long id;
    private String name;
    private Calendar eventDateTime;
    private BigDecimal totalEventSum;
    private List<String> eventUserLIst;
    private Integer amountOfExpense;
    private List<ExpenseDto> expenseList;

    public EventDto(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventDateTime = event.getEventDateTime();
        this.totalEventSum = event.getTotalEventSum();
        this.amountOfExpense = event.getExpenseList().size();
        this.eventUserLIst = event.getEventUserList().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        this.expenseList = event.getExpenseList().stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());
    }
}
