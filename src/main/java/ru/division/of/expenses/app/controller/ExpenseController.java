package ru.division.of.expenses.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.service.EventService;
import ru.division.of.expenses.app.service.ExpenseService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return expenseService.findById(id);
    }

    @GetMapping
    public List<ExpenseDto> findAll(@RequestParam(required = false, defaultValue = "1") int page ,
                                 @RequestParam(required = false, defaultValue = "5") int size){
        if (page <= 0) {
            page = 1;
        }
        return expenseService.findAll(page, size);
    }

    @GetMapping("/ByEventId/{id}")
    public List<ExpenseDto> findExpenseByEventId(@PathVariable Long id,
                                            @RequestParam(required = false, defaultValue = "1") int page ,
                                            @RequestParam(required = false, defaultValue = "5") int size){
        if (page <= 0) {
            page = 1;
        }
        return eventService.findExpenseByEventId(id, page, size);
    }

    @PostMapping
    public void saveExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
    }

    @PostMapping("/addToEvent/{eventId}")
    public void saveAndAddToEvent(
            Principal principal,
            @PathVariable Long eventId,
            @RequestBody Expense expense){
        expenseService.saveAndAddToEvent(principal.getName(), eventId, expense);
    }

    // добавление трат к эвенту без принципала, через Дто.
    @PostMapping("/addByEventId/{eventId}")
    public void saveAndAddToEventNoPrinciple(
            @PathVariable Long eventId,
            @RequestBody ExpenseDto expenseDto){
        expenseService.saveAndAddToEventNoPrinciple(eventId, expenseDto);
    }

//    @PutMapping
//    public ResponseEntity<?> updateExpense(
//            @RequestBody ExpenseDto expenseDto)
//    {
//
//        ResponseEntity<?> responseEntity = expenseService.updateExpense(expenseDto);
////        return expenseService.updateExpense(expenseDto);
//        return responseEntity;
//    }

    @PatchMapping
    public ResponseEntity<?> updateExpense(
            @RequestBody ExpenseDto expenseDto)
    {

        ResponseEntity<?> responseEntity = expenseService.updateExpense(expenseDto);
//        return expenseService.updateExpense(expenseDto);
        return responseEntity;
    }


    // Удаление Траты только байером этой Траты
    // или менеджером Евента, к которому относится данная Трата.
    @DeleteMapping("/{expenseId}")
    public void deleteExpenseByPrincipal(
            Principal principal,
            @PathVariable Long expenseId){
        expenseService.deleteExpenseByPrincipal(principal.getName(), expenseId);
    }


}
