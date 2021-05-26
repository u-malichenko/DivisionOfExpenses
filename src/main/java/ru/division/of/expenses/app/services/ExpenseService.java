package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptions_handling.ExpenseNotFoundException;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.repositoryes.ExpenseRepository;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Page<Expense> findAll(
            int page,
            int size
    ) {
        return expenseRepository.findAll(PageRequest.of(page, size));
    }

    public ResponseEntity<?> findById(Long id) {
        ExpenseDto expenseDto = new ExpenseDto(findExpenseByIdBasic(id));
        if (expenseDto.getId() != null) {
            return new ResponseEntity<ExpenseDto>(expenseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }


    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }


    public ResponseEntity<?> updateExpense(Expense expense){
        Expense expenseFromDB = findExpenseByIdBasic(expense.getId());

        if(expenseFromDB.getId() != null){
            expenseFromDB.setExpenseDate(expense.getExpenseDate());
            expenseFromDB.setTotalExpenseSum(expense.getTotalExpenseSum());
            expenseFromDB.setComment(expense.getComment());
            expenseFromDB.setBuyer(expense.getBuyer());
            return new ResponseEntity<Expense>(expenseRepository.save(expenseFromDB), HttpStatus.OK);
        }else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

    private Expense findExpenseByIdBasic(Long id){
        Expense expense = new Expense();
        try {
            expense = expenseRepository.findById(id)
                    .orElseThrow(
                            () -> new ExpenseNotFoundException("Event: " + id + " not found.")
                    );
        }catch (ExpenseNotFoundException e){
//            e.printStackTrace();
            System.out.println(e);
        }
        return expense;
    }


}
