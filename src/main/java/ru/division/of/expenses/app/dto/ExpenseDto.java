package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {

    private Long id;
    private String buyer;
    private Calendar expenseDate;
    private String comment;
    private BigDecimal totalExpenseSum;
    private List<String> directPayersList;
    private List<String> partitialPayersList;

    public ExpenseDto(Expense expense) {
        this.id = expense.getId();
        if (expense.getBuyer() != null){
        this.buyer = expense.getBuyer().getUsername();}
        this.expenseDate = expense.getExpenseDate();
        this.comment = expense.getComment();
        this.totalExpenseSum = expense.getTotalExpenseSum();
        this.directPayersList = expense.getDirectPayersList().stream()
        .map(DirectPayer::getUser)
        .collect(Collectors.toList())
        .stream()
        .map(User::getUsername)
        .collect(Collectors.toList());
        this.partitialPayersList = expense.getPartitialPayersList().stream()
        .map(PartitialPayer::getUser)
        .collect(Collectors.toList())
        .stream()
        .map(User::getUsername)
        .collect(Collectors.toList());
    }
}
