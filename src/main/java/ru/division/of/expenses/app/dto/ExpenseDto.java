package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDto {

    private Long id;
    private String buyer;
    private String comment;
    private BigDecimal totalExpenseSum;
    private Integer numberOfExpenseParticipants;

    public ExpenseDto(Expense expense) {
        this.id = expense.getId();
        if (expense.getBuyer() != null){
            this.buyer = expense.getBuyer().getUsername();
        }
        this.comment = expense.getComment();
        this.totalExpenseSum = expense.getTotalExpenseSum();
        this.numberOfExpenseParticipants = this.buyer.isEmpty() ? 0 : 1 +
                expense.getDirectPayersList().size() +
                expense.getDirectPayersList().size();
    }
}
