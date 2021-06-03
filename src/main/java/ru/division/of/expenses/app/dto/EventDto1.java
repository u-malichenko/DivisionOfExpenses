package ru.division.of.expenses.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.User;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto1 {

    private Long id;
    private String name;
    private Calendar eventDateTime;
    private String description;
    private String managerUsername;
    private BigDecimal totalEventSum;
    private List<String> eventUserList;
    private List<ExpenseDto> expenseList;

    public EventDto1(Event event) {

        this.id = event.getId();
        this.name = event.getName();
        this.eventDateTime = event.getEventDateTime();
        this.description = event.getDescription();
        if(event.getEventManager() != null){
            this.managerUsername = event.getEventManager().getUsername(); }
        this.totalEventSum = event.getTotalEventSum();
        this.eventUserList = event.getEventUserList().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        this.expenseList = event.getExpenseList().stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());
    }
}
