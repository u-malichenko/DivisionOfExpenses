package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.DirectPayer;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.models.User;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {

    private Long id;
    private String name;
    private Calendar eventDateTime;
    private String description;
    private String managerUsername;
    private BigDecimal totalEventSum;
    private List<String> eventUserLIst;
    private List<ExpenseDto> expenseList;

    public EventDto(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventDateTime = event.getEventDateTime();
        this.description = event.getDescription();
        if(event.getEventManager() != null){
        this.managerUsername = event.getEventManager().getUsername(); }
        this.totalEventSum = event.getTotalEventSum();
        this.eventUserLIst = event.getEventUserLIst().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        Collection<Expense> collection = event.getExpenseList();
        System.out.println(collection.size());
        this.expenseList = event.getExpenseList().stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());

    }
}
