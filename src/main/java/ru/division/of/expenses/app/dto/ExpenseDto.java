package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.*;

import java.math.BigDecimal;
import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {

    private Long id;
    private String buyer;
    private String comment;
    private String event;
    private Calendar expenseDate;
    private BigDecimal totalExpenseSum;
    private Integer numberOfExpenseParticipants;

    public ExpenseDto(Expense expense) {
        this.id = expense.getId();
        if (expense.getBuyer() != null){
            this.buyer = expense.getBuyer().getUsername();
        }
        if (expense.getEvent() != null){
            this.event = expense.getEvent().getTitle();
        }
        this.comment = expense.getComment();
        this.expenseDate=expense.getExpenseDate();
        this.totalExpenseSum = expense.getTotalExpenseSum();
        this.numberOfExpenseParticipants = this.buyer == null ? 0 : 1 +
                expense.getDirectPayersList().size() +
                expense.getPartitialPayersList().size();
    }
}
