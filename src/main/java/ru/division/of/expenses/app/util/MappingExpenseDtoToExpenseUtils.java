package ru.division.of.expenses.app.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptionhandling.EventNotFoundException;
import ru.division.of.expenses.app.exceptionhandling.UserNotFoundException;
import ru.division.of.expenses.app.model.DirectPayer;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.PartitialPayer;
import ru.division.of.expenses.app.repository.DirectPayersRepository;
import ru.division.of.expenses.app.repository.ExpenseRepository;
import ru.division.of.expenses.app.repository.PartitialPayersRepository;
import ru.division.of.expenses.app.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MappingExpenseDtoToExpenseUtils {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final PartitialPayersRepository partitialPayersRepository;
    private final DirectPayersRepository directPayersRepository;

    public Expense mapToExpenseFoSave(ExpenseDto expenseDto){

        Expense expense = new Expense();

        if(expenseDto.getId() != null){
            expense = expenseRepository.findById(expenseDto.getId()).orElse(new Expense());
        }
        if(expenseDto.getBuyer() != null){
//            expense.setBuyer(userRepository.findByUsername(expenseDto.getBuyer()).get());
            try {
                expense.setBuyer(userRepository.findByUsername(expenseDto.getBuyer()).orElseThrow(
                        () -> new UserNotFoundException("Not found")
                ));
            }catch (UserNotFoundException e){
                System.out.println(e);
                expense.setBuyer(null);
            }

        }
        expense.setTotalExpenseSum(expenseDto.getTotalExpenseSum());
        expense.setComment(expenseDto.getComment());
        expense.setExpenseDate(expenseDto.getExpenseDate());
        return expense;
    }

    public Expense mapToExpenseFoUpdate(ExpenseDto expenseDto){
        Expense expense = mapToExpenseFoSave(expenseDto);

        if(expense.getPartitialPayersList() != null) {
            for (PartitialPayer partitialPayer : partitialPayersRepository.findByExpense(expense).get()) {
                partitialPayersRepository.delete(partitialPayer);
            }
        }

        if(expense.getDirectPayersList() != null) {
            for (DirectPayer directPayer : directPayersRepository.findByExpense(expense).get()) {
                directPayersRepository.delete(directPayer);
            }
        }

        return expense;

    }

    public void savePayersOfExpense(ExpenseDto expenseDto, Expense expense){

        for (Map.Entry<String, BigDecimal> o : expenseDto.getPartitialPayerMap().entrySet()) {
            PartitialPayer partitialPayer = new PartitialPayer();
            partitialPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            partitialPayer.setCoefficient(o.getValue());
            partitialPayer.setExpense(expense);
            partitialPayersRepository.save(partitialPayer);
        }

        for (Map.Entry<String, BigDecimal> o : expenseDto.getDirectPayerMap().entrySet()){
            DirectPayer directPayer = new DirectPayer();
            directPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            directPayer.setSumma(o.getValue());
            directPayer.setExpense(expense);
            directPayersRepository.save(directPayer);
        }
    }


}
