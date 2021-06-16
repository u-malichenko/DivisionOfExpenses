package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public List<ExpenseDto> findAll(String username) {
        List<Expense> buyerList = expenseRepository.findExpenseByBuyerUsername(username);
        List<Expense> directPayerList = expenseRepository.findExpenseByDirectPayersListByUsername(username);
        List<Expense> pertitialPayerList = expenseRepository.findExpenseByPartitialPayersListByUsername(username);
        buyerList.addAll(directPayerList);
        buyerList.addAll(pertitialPayerList);
        Set<Expense> expenses = new HashSet<>(buyerList);
        return expenses
                .stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> findById(String username, Long expenseId) {
            ExpenseDto expenseDto = new ExpenseDto(findExpenseByIdBasic(expenseId));
            if (expenseDto.getId() != null) {
                if(username.equals(expenseRepository.findBuyerUsernameByExpenseId(expenseId))
                        || username.equals(eventService.findEventManagerUsernameByExpenseId(expenseId))
                        || expenseRepository.findDirectPayerUsernameList(expenseId).contains(username)
                        || expenseRepository.findPartitialPayerUsernameList(expenseId).contains(username)){
                    return new ResponseEntity<ExpenseDto>(expenseDto, HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<>("You are not in", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NOT_ACCEPTABLE);
            }
    }


    public Expense saveExpense(Expense expense) {
        Expense newExpense=expenseRepository.save(expense);
        divisionOfExpenseService.calculateEvent(newExpense.getEvent());
        return newExpense;
    }


    public Expense updateExpense(Expense expense) {
        Expense expenseFromDB = findExpenseByIdBasic(expense.getId());

        if (expenseFromDB.getId() != null) {
            expenseFromDB.setExpenseDate(expense.getExpenseDate());
            expenseFromDB.setTotalExpenseSum(expense.getTotalExpenseSum());
            expenseFromDB.setComment(expense.getComment());
            expenseFromDB.setBuyer(expense.getBuyer());
            expenseFromDB.setPartitialPayersList(expense.getPartitialPayersList());
            expenseFromDB.setDirectPayersList(expense.getDirectPayersList());
            return expenseRepository.save(expenseFromDB);
        } else {
            return null;
        }
    }

    public ResponseEntity<?> updateExpense(String username, ExpenseDto expenseDto) {
        if(username.equals(expenseRepository.findBuyerUsernameByExpenseId(expenseDto.getId())) ||
                username.equals(eventService.findEventManagerUsernameByExpenseId(expenseDto.getId()))){
            Expense expense = mappingExpenseDtoToExpenseUtils.mapToExpenseFoUpdate(expenseDto);
            Expense newExpense = updateExpense(expense);
            if(newExpense != null){
                mappingExpenseDtoToExpenseUtils.savePayersOfExpense(expenseDto, newExpense);
                divisionOfExpenseService.calculateEvent(newExpense.getEvent());
                return new ResponseEntity<String>("Expense was successfully updated", HttpStatus.ACCEPTED);
            }
        }
            return new ResponseEntity<>("Update is not allowed", HttpStatus.NOT_ACCEPTABLE);
    }

    private Expense findExpenseByIdBasic(Long id) {
        Expense expense = new Expense();
        try {
            expense = expenseRepository.findById(id)
                    .orElseThrow(
                            () -> new ExpenseNotFoundException("Expense: " + id + " not found.")
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

    public void saveAndAddToEventByPrinciple(String username, Long eventId, ExpenseDto expenseDto) {
        List<String> eventUserList = eventService.findEventUserUsernameById(eventId);
        if(!eventUserList.contains(username)){
            return;
        }
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
        Expense expense = mappingExpenseDtoToExpenseUtils.mapToExpenseFoSave(expenseDto);
        expense.setEvent(eventService.findEventByIdBasic(eventId));
        Expense newExpense = expenseRepository.save(expense);
        mappingExpenseDtoToExpenseUtils.savePayersOfExpense(expenseDto, newExpense);
        divisionOfExpenseService.calculateEvent(newExpense.getEvent());
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
