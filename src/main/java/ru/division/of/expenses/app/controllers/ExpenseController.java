package ru.division.of.expenses.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.services.EventService;
import ru.division.of.expenses.app.services.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final EventService eventService;

//    @GetMapping("/{id}")
//    public Optional<Expense> findById(@PathVariable Long id){
//        return expenseService.findById(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return expenseService.findById(id);
    }

    @GetMapping
    public Page<Expense> findAll(@RequestParam(required = false, defaultValue = "0") int page ,
                                 @RequestParam(required = false, defaultValue = "10") int size){
        return expenseService.findAll(page, size);
    }

    @GetMapping("/event/{id}")
    public List<ExpenseDto> findExpenseById(@PathVariable Long id,
                                            @RequestParam(required = false, defaultValue = "1") int page ,
                                            @RequestParam(required = false, defaultValue = "5") int size){
        return eventService.findExpenseById(id, page, size);
    }

    @PostMapping
    public Expense saveEvent(@RequestBody ExpenseDto expenseDto) {
        return expenseService.saveExpenxe(expenseDto);
    }

    @PutMapping
    public ResponseEntity<?> updateExpence(@RequestBody ExpenseDto expenseDto){
        return expenseService.updateExpense(expenseDto);
    }
}
