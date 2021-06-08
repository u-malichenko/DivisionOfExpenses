package ru.division.of.expenses.app.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.repository.ExpenseRepository;
import ru.division.of.expenses.app.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class MappingExpenseDtoToExpenseUtils {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public Expense mapToExpense(ExpenseDto expenseDto){

        Expense expense = new Expense();

        if(expenseDto.getId() != null){
            expense = expenseRepository.findById(expenseDto.getId()).orElse(new Expense());
        }
        if(expenseDto.getBuyer() != null){
            expense.setBuyer(userRepository.findByUsername(expenseDto.getBuyer()).get());
        }
        expense.setTotalExpenseSum(expenseDto.getTotalExpenseSum());
        expense.setComment(expenseDto.getComment());
        expense.setExpenseDate(expenseDto.getExpenseDate());

        return expense;

    }
}
