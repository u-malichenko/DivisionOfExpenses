package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundException;
import ru.division.of.expenses.app.exceptions_handling.ExpenseNotFoundException;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.EventMember;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.repositoryes.EventMemberRepository;
import ru.division.of.expenses.app.repositoryes.ExpenseRepository;
import ru.division.of.expenses.app.repositoryes.UserRepository;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;
import ru.division.of.expenses.app.utils.MappingExpenseDtoToExpenseUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    //    private final EventRepository eventRepository;
    private final EventService eventService;
    private final MappingExpenseDtoToExpenseUtils mappingExpenseDtoToExpenseUtils;
    private final EventMemberRepository eventMemberRepository;

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
        return expenseRepository.save(expense);
    }


    public ResponseEntity<?> updateExpense(Expense expense) {
        Expense expenseFromDB = findExpenseByIdBasic(expense.getId());

        if (expenseFromDB.getId() != null) {
            expenseFromDB.setExpenseDate(expense.getExpenseDate());
            expenseFromDB.setTotalExpenseSum(expense.getTotalExpenseSum());
            expenseFromDB.setComment(expense.getComment());
            expenseFromDB.setBuyer(expense.getBuyer());
            return new ResponseEntity<Expense>(expenseRepository.save(expenseFromDB), HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
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


    public void deleteExpense(Long id) {
        try {
            Expense expense = expenseRepository.findById(id)
                    .orElseThrow(
                            () -> new EventNotFoundException("Expense: " + id + " not found.")
                    );
            expenseRepository.delete(expense);
        } catch (EventNotFoundException e) {
            System.out.println(e);
        }

    }
}
