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
import java.util.ArrayList;
import java.util.List;
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
        if(expense.getPartitialPayersList() != null) {
            for (PartitialPayer partitialPayer : partitialPayersRepository.findByExpense(expense).get()) {
                partitialPayersRepository.delete(partitialPayer);
            }
        }
        List<PartitialPayer> newPartitialPayerList = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> o : expenseDto.getPartitialPayerMap().entrySet()) {
            PartitialPayer partitialPayer = new PartitialPayer();
            partitialPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            partitialPayer.setCoefficient(o.getValue());
            newPartitialPayerList.add(partitialPayer);
            partitialPayer.setExpense(expense);
            partitialPayersRepository.save(partitialPayer);
        }

        if(expense.getDirectPayersList() != null) {
            for (DirectPayer directPayer : directPayersRepository.findByExpense(expense).get()) {
                directPayersRepository.delete(directPayer);
            }
        }

        List<DirectPayer> newDirectPayerList = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> o : expenseDto.getDirectPayerMap().entrySet()){
            DirectPayer directPayer = new DirectPayer();
            directPayer.setUser(userRepository.findByUsername(o.getKey()).get());
            directPayer.setSumma(o.getValue());
            newDirectPayerList.add(directPayer);
            directPayer.setExpense(expense);
            directPayersRepository.save(directPayer);
        }


        return expense;

    }


}
