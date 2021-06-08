package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.model.DirectPayer;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.PartitialPayer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, BigDecimal> directPayerMap = new HashMap<>();
    private Map<String, BigDecimal> partitialPayerMap = new HashMap<>();


    public ExpenseDto(Expense expense) {
        this.id = expense.getId();
        if (expense.getBuyer() != null) {
            this.buyer = expense.getBuyer().getUsername();
        }
        if (expense.getEvent() != null) {
            this.event = expense.getEvent().getTitle();
        }
        this.comment = expense.getComment();
        this.expenseDate = expense.getExpenseDate();
        this.totalExpenseSum = expense.getTotalExpenseSum();
        this.numberOfExpenseParticipants = this.buyer == null ? 0 : 1 +
                expense.getDirectPayersList().size() +
                expense.getPartitialPayersList().size();
        for (DirectPayer directPayer : expense.getDirectPayersList()) {
            directPayerMap.put(directPayer.getUser().getUsername(), directPayer.getSumma());
        }
        for (PartitialPayer partitialPayer : expense.getPartitialPayersList()) {
            partitialPayerMap.put(partitialPayer.getUser().getUsername(), partitialPayer.getCoefficient());
        }
    }
}
