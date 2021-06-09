package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptionhandling.ExpenseNotFoundException;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.EventMember;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.User;
import ru.division.of.expenses.app.repository.EventMemberRepository;
import ru.division.of.expenses.app.repository.ExpenseRepository;
import ru.division.of.expenses.app.repository.UserRepository;
import ru.division.of.expenses.app.util.EmptyJsonResponse;
import ru.division.of.expenses.app.util.MappingExpenseDtoToExpenseUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final EventService eventService;
    private final MappingExpenseDtoToExpenseUtils mappingExpenseDtoToExpenseUtils;
    private final EventMemberRepository eventMemberRepository;
    private final DivisionOfExpenseService divisionOfExpenseService;

    public List<ExpenseDto> findAll(
            int page,
            int size
    ) {
        Page<Expense> expenses = expenseRepository.findAll(PageRequest.of(page - 1, size));
        return expenses
                .stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> findById(Long id) {
        ExpenseDto expenseDto = new ExpenseDto(findExpenseByIdBasic(id));
        if (expenseDto.getId() != null) {
            return new ResponseEntity<ExpenseDto>(expenseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }


    public Expense saveExpense(Expense expense) {
        Expense newExpense=expenseRepository.save(expense);
        divisionOfExpenseService.calculateEvent(newExpense.getEvent());
        return newExpense;
    }


    public ResponseEntity<?> updateExpense(Expense expense) {
        Expense expenseFromDB = findExpenseByIdBasic(expense.getId());

        if (expenseFromDB.getId() != null) {
            expenseFromDB.setExpenseDate(expense.getExpenseDate());
            expenseFromDB.setTotalExpenseSum(expense.getTotalExpenseSum());
            expenseFromDB.setComment(expense.getComment());
            expenseFromDB.setBuyer(expense.getBuyer());
            divisionOfExpenseService.calculateExpense(expenseFromDB, expenseFromDB.getEvent().getEventMembers());
            expenseRepository.save(expenseFromDB);
            return new ResponseEntity<String>("Expense was successfully updated", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<?> updateExpense(ExpenseDto expenseDto) {
        Expense expense = mappingExpenseDtoToExpenseUtils.mapToExpense(expenseDto);
        return updateExpense(expense);
    }

    private Expense findExpenseByIdBasic(Long id) {
        Expense expense = new Expense();
        try {
            expense = expenseRepository.findById(id)
                    .orElseThrow(
                            () -> new ExpenseNotFoundException("Event: " + id + " not found.")
                    );
        } catch (ExpenseNotFoundException e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return expense;
    }

    public void saveAndAddToEvent(String username, Long eventId, Expense expense) {
        expense.setBuyer(userRepository.findByUsername(username).get());
        expense.setEvent(eventService.findEventByIdBasic(eventId));
        divisionOfExpenseService.calculateEvent(expense.getEvent());
        expenseRepository.save(expense);
    }

    public void saveAndAddToEventNoPrinciple(Long eventId, ExpenseDto expenseDto) {
        List<String> eventUserUsernameList = eventService.findEventUserUsernameById(eventId);
        List<String> eventMemberUserUsername = eventService.findEventMemberUsernameById(eventId);
        if (!eventUserUsernameList.contains(expenseDto.getBuyer())) {
            Event event = eventService.findEventByIdBasic(eventId);
            event.getEventUserList().add(userRepository.findByUsername(expenseDto.getBuyer()).get());
            eventService.updateEvent(event);
        }
        if (!eventMemberUserUsername.contains(expenseDto.getBuyer())) {
            Event event = eventService.findEventByIdBasic(eventId);

            User expenseBuyer = userRepository.findByUsername(expenseDto.getBuyer()).get();
            EventMember eventMember = new EventMember();
            eventMember.setUser(expenseBuyer);
            eventMember.setEvent(event);
            eventMemberRepository.save(eventMember);
            event.getEventMembers().add(eventMember);
            eventService.updateEvent(event);
        }
        Expense expense = mappingExpenseDtoToExpenseUtils.mapToExpense(expenseDto);
        expense.setEvent(eventService.findEventByIdBasic(eventId));
        expenseRepository.save(expense);
    }


    // Удаление Траты только байером этой Траты
    // или менеджером Евента, к которому относится данная Трата.
    public void deleteExpenseByPrincipal(String username, Long expenseId) {
        if(username.equals(expenseRepository.findBuyerUsernameByExpenseId(expenseId)) ||
                username.equals(eventService.findEventManagerUsernameByExpenseId(expenseId))){
            deleteExpense(expenseId);
        }
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = findExpenseByIdBasic(expenseId);
        Event event=expense.getEvent();
        expenseRepository.delete(expense);
        divisionOfExpenseService.calculateEvent(event);
    }
}
