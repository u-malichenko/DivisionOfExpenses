package ru.division.of.expenses.app.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.ExpenseDto;
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
//        if(expense.getPartitialPayersList() != null) {
        if(expense.getPartitialPayersList().size() > 0) {
            for (PartitialPayer partitialPayer : partitialPayersRepository.findByExpense(expense).get()) {
                partitialPayersRepository.delete(partitialPayer);
            }
        }
        for (Map.Entry<String, BigDecimal> o : expenseDto.getPartitialPayerMap().entrySet()) {
            PartitialPayer partitialPayer = new PartitialPayer();
            partitialPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            partitialPayer.setCoefficient(o.getValue());
            partitialPayer.setExpense(expense);
            partitialPayersRepository.save(partitialPayer);
        }

//        if(expense.getDirectPayersList() != null) {
        if(expense.getDirectPayersList().size() > 0) {
            for (DirectPayer directPayer : directPayersRepository.findByExpense(expense).get()) {
                directPayersRepository.delete(directPayer);
            }
        }

        for (Map.Entry<String, BigDecimal> o : expenseDto.getDirectPayerMap().entrySet()){
            DirectPayer directPayer = new DirectPayer();
            directPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            directPayer.setSumma(o.getValue());
            directPayer.setExpense(expense);
            directPayersRepository.save(directPayer);
        }


        return expense;

    }


}
