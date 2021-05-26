package ru.division.of.expenses.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.DirectPayer;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.models.PartitialPayer;
import ru.division.of.expenses.app.models.User;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto1 {

    private Long id;
    private String buyer;
    private Calendar expenseDate;
    private String comment;
    private BigDecimal totalExpenseSum;
    private List<String> directPayersList;
    private List<String> partitialPayersList;

    public ExpenseDto1(Expense expense){

        this.id = expense.getId();
        if (expense.getBuyer() != null){
            this.buyer = expense.getBuyer().getUsername();
        }
        this.expenseDate = expense.getExpenseDate();
        this.comment = expense.getComment();
        this.totalExpenseSum = expense.getTotalExpenseSum();
        this.directPayersList = expense.getDirectPayersList().stream()
                .map(DirectPayer::getUser)
                .map(User::getUsername)
                .collect(Collectors.toList());
        this.partitialPayersList = expense.getPartitialPayersList().stream()
                .map(PartitialPayer::getUser)
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
