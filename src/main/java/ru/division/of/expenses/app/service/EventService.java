package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.*;
import ru.division.of.expenses.app.exceptionhandling.EventNotFoundException;
import ru.division.of.expenses.app.model.*;
import ru.division.of.expenses.app.repository.*;
import ru.division.of.expenses.app.util.EmptyJsonResponse;
import ru.division.of.expenses.app.util.MappingEventUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMemberRepository eventMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final DivisionOfExpenseService divisionOfExpenseService;
    private final PartitialPayersRepository partitialPayersRepository;
    private final DirectPayersRepository directPayersRepository;
    private final MappingEventUtils mappingEventUtils;

    public ResponseEntity<?> findEventById(Long id) {
        EventDto1 eventDto1 = new EventDto1(findEventByIdBasic(id));
        if (eventDto1.getId() != null) {
            return new ResponseEntity<EventDto1>(eventDto1, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<?> findEventDtoForEditPageByIdByParticipant(String username, Long id) {
        if(!eventRepository.findEventUserUsernameById(id).contains(username)){
            return new ResponseEntity<String>("You are not a participant", HttpStatus.NOT_FOUND);
        }
        EventDtoForEditPage eventDtoForEditPage = new EventDtoForEditPage(findEventByIdBasic(id));
        if (eventDtoForEditPage.getId() != null) {
            return new ResponseEntity<EventDtoForEditPage>(eventDtoForEditPage, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public List<EventDto> findAll(
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findAll(PageRequest.of(page - 1, size));
        return events
                .stream()
                .map(EventDto::new)
                .collect(Collectors.toList());
    }


    public Event saveEvent(Event event, String username) {
        User user = userService.findByUsernameBasic(username);
        event.setEventManager(user);
        event.getEventUserList().add(user);
        EventMember eventMember = new EventMember();
        eventMember.setUser(user);
        event.getEventMembers().add(eventMember);
        Event savedEvent = eventRepository.save(event);
        eventMember.setEvent(savedEvent);
        eventMemberRepository.save(eventMember);
        divisionOfExpenseService.calculateEvent(savedEvent);
        return savedEvent;
    }

    public EventDtoForEditPage saveEventReturnDto(Event event, String username) {
        Event savedEvent = saveEvent(event, username);
        return mappingEventUtils.mapToEventDtoForEditPage(event);
    }


    public ResponseEntity<?> updateEvent(Event event) {
        Event eventFromDB = findEventByIdBasic(event.getId());
        if (eventFromDB.getId() != null) {
            if (event.getTitle() != null) {
                eventFromDB.setTitle(event.getTitle());
            }
            if (event.getDescription() != null) {
                eventFromDB.setDescription(event.getDescription());
            }
//            if (event.getTotalEventSum() != null) {
//                eventFromDB.setTotalEventSum(event.getTotalEventSum());
//            }
            if (event.getEventUserList() != null) {
                eventFromDB.setEventUserList(event.getEventUserList());
            }
            if (event.getEventManager() != null) {
                eventFromDB.setEventManager(event.getEventManager());
            }
            Event savedEvent = eventRepository.save(eventFromDB);
            divisionOfExpenseService.calculateEvent(savedEvent);
//            return new ResponseEntity<Event>(savedEvent, HttpStatus.OK);
            return new ResponseEntity<String>("Well done", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<?> updateEventByPrincipal(Event event, String username) {
        if (!username.equals(eventRepository.findEventManagerUsernameById(event.getId()))) {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
        return updateEvent(event);
    }

    public ResponseEntity<?> updateEventByEventDtoForEditPageByManager(EventDtoForEditPage eventDtoForEditPage, String username) {
        if (!username.equals(eventRepository.findEventManagerUsernameById(eventDtoForEditPage.getId()))) {
            return new ResponseEntity<String>("You don't have any right to update", HttpStatus.NOT_FOUND);
        }
        Set<String> eventUserSet = eventDtoForEditPage.getEventUserList().stream().collect(Collectors.toSet());
        if(eventUserSet.size() != eventDtoForEditPage.getEventUserList().size()){
            return new ResponseEntity<String>("Duplicate username", HttpStatus.NOT_ACCEPTABLE);
        }
        Event eventFromDB = findEventByIdBasic(eventDtoForEditPage.getId());
        List<String> eventUserListDto = eventDtoForEditPage.getEventUserList();
        List<String> eventUserListDB = eventRepository.findEventUserUsernameById(eventFromDB.getId());
        List<String> result1 = eventUserListDB.stream().filter(aObject ->
                !eventUserListDto.contains(aObject)).collect(Collectors.toList());
        if(result1.size() != 0){
            for(String o : result1){
                removeUserFromEventMemberList(o, eventFromDB);
            }
        }
        List<String> result2 = eventUserListDto.stream().filter(aObject ->
                !eventUserListDB.contains(aObject)).collect(Collectors.toList());
        if(result2.size() != 0){
            for (String o : result2){
                addUserToEventMemberList(o, eventFromDB);
            }
        }
        List<User> userList = null;
        if (!eventDtoForEditPage.getEventUserList().isEmpty()) {
            userList = new ArrayList<>();
            for (String eventUserUsername : eventDtoForEditPage.getEventUserList()
            ) {
                User user = userService.findByUsernameBasic(eventUserUsername);
                userList.add(user);
            }
        }
        Event event = mappingEventUtils.mapEventDtoForEditPageToEvent(eventFromDB, eventDtoForEditPage, userList);
        return updateEvent(event);
    }


    public void deleteEventByManager(Long id, String username) {
        if (!username.equals(eventRepository.findEventManagerUsernameById(id))) {
            return;
        }
        deleteEvent(id);
    }

    public void deleteEvent(Long id) {
        Event eventFromDB = findEventByIdBasic(id);
        eventRepository.delete(eventFromDB);
    }

    public List<EventDto> findEventsByManagerId(
            Long id,
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findEventsByManagerId(id, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map(EventDto::new)
                .collect(Collectors.toList());
    }

    public List<EventDto> findEventsByManagerUsername(
            String username,
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findEventsByManagerUsername(username, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map((EventDto::new))
                .collect(Collectors.toList());
    }

    public List<EventDto> findEventsByParticipant(
            String username,
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findEventsByParticipantUsername(username, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map((EventDto::new))
                .collect(Collectors.toList());
    }

    public List<ExpenseDto> findExpenseByEventId(
            Long id,
            int page,
            int size
    ) {
        Page<Expense> expenses = eventRepository.findExpenseByEventId(id, PageRequest.of(page - 1, size));
        return expenses
                .stream()
                .map(ExpenseDto::new)
                .collect(Collectors.toList());
    }

    public List<EventDto> findEventByParticipantId(
            Long id,
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findEventByParticipantId(id, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map(EventDto::new)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> addUserToEventUserList(String username, Long eventId) {
        User user = userService.findByUsernameBasic(username);
        Event event = findEventByIdBasic(eventId);
        event.getEventUserList().add(user);
        addUserToEventMemberList(username, event);
        return updateEvent(event);
    }

    public void addUserToEventMemberList(String username, Event event) {
        User user = userService.findByUsernameBasic(username);
        EventMember eventMember = new EventMember();
        eventMember.setUser(user);
        eventMember.setEvent(event);
        eventMember.setSaldo(new BigDecimal(0.00));
        eventMemberRepository.save(eventMember);
    }

    public void removeUserFromEventUserList(UserDtoRemove userDtoRemove, Long eventId){
        User user = userService.findByUsernameBasic(userDtoRemove.getUsername());
        Event event = findEventByIdBasic(eventId);
        event.getEventUserList().remove(user);
        removeUserFromEventMemberList(userDtoRemove.getUsername(), event);
        updateEvent(event);
    }

    public void removeUserFromEventMemberList(String username, Event event){
        User user = userService.findByUsernameBasic(username);
        EventMember eventMember = eventMemberRepository.findEventMemberByEventAndUser(event, user).get();
        eventMemberRepository.delete(eventMember);
        List<Expense> expenseList = expenseRepository.findExpenseByEvent(event);
        for(Expense expense : expenseList){
            PartitialPayer partitialPayer = null;
            try {
                partitialPayer = partitialPayersRepository.findPartitialPayerByExpenseAndUser(expense, user)
                        .orElseThrow(
                                () -> new EventNotFoundException("Not found")
                        );
                partitialPayersRepository.delete(partitialPayer);
            } catch (EventNotFoundException e) {
//                e.printStackTrace();
            }
            DirectPayer directPayer = null;
            try {
                directPayer = directPayersRepository.findDirectPayerByExpenseAndUser(expense, user)
                        .orElseThrow(
                                () -> new EventNotFoundException("Not found")
                        );
                directPayersRepository.delete(directPayer);
            } catch (EventNotFoundException e) {
//                e.printStackTrace();
            }
        }
        for(Expense expense : expenseList){
            if(expense.getBuyer().getUsername().equals(user.getUsername())){
                expenseRepository.delete(expense);
            }
        }

    }


    public List<String> findEventUserUsernameById(Long eventId) {
        return eventRepository.findEventUserUsernameById(eventId);
    }

    public Event findEventByIdBasic(Long id) {
        Event event = new Event();
        try {
            event = eventRepository.findById(id)
                    .orElseThrow(
                            () -> new EventNotFoundException("Event: " + id + " not found.")
                    );
        } catch (EventNotFoundException e) {
            System.out.println(e);
        }
        return event;
    }


    public List<String> findEventMemberUsernameById(Long eventId) {
        return eventRepository.findEventMemberUsernameById(eventId);
    }

    public String findEventManagerUsernameByExpenseId(Long expenseId) {
        return eventRepository.findEventManagerUsernameByExpenseId(expenseId);
    }
}
