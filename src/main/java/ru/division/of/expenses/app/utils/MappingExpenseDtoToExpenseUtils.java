package ru.division.of.expenses.app.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.repositoryes.UserRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class MappingExpenseDtoToExpenseUtils {

    private final UserRepository userRepository;
//    private Long id;
//    private String buyer;
//    private String comment;
//    private String event;
//    private BigDecimal totalExpenseSum;
//    private Integer numberOfExpenseParticipants;

    public Expense mapToExpense(ExpenseDto expenseDto){

        Expense expense = new Expense();

        if(expenseDto.getId() != null){
            expense.setId(expenseDto.getId());
        }
        if(expenseDto.getBuyer() != null){
            expense.setBuyer(userRepository.findByUsername(expenseDto.getBuyer()).get());
        }
        expense.setTotalExpenseSum(expenseDto.getTotalExpenseSum());
        expense.setComment(expenseDto.getComment());

        return expense;

    }
}
