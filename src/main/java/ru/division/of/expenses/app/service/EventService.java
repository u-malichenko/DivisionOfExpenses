package ru.division.of.expenses.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.EventDto1;
import ru.division.of.expenses.app.dto.EventDtoForEditPage;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptionhandling.EventNotFoundException;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.EventMember;
import ru.division.of.expenses.app.model.Expense;
import ru.division.of.expenses.app.model.User;
import ru.division.of.expenses.app.repository.EventMemberRepository;
import ru.division.of.expenses.app.repository.EventRepository;
import ru.division.of.expenses.app.util.EmptyJsonResponse;
import ru.division.of.expenses.app.util.MappingEventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    //    private final UserRepository userRepository;
    private final UserService userService;
    private final EventMemberRepository eventMemberRepository;
    private final DivisionOfExpenseService divisionOfExpenseService;
    private final MappingEventUtils mappingEventUtils;

    public ResponseEntity<?> findEventById(Long id) {
        EventDto1 eventDto1 = new EventDto1(findEventByIdBasic(id));
        if (eventDto1.getId() != null) {
            return new ResponseEntity<EventDto1>(eventDto1, HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> findEventDtoForEditPageById(Long id) {
        EventDtoForEditPage eventDtoForEditPage = new EventDtoForEditPage(findEventByIdBasic(id));
        if (eventDtoForEditPage.getId() != null) {
            return new ResponseEntity<EventDtoForEditPage>(eventDtoForEditPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
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
//        User user = userRepository.findByUsername(username).get();
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

//    public EventDto saveEventDto(Event event){
//        return new EventDto(eventRepository.save(event));
//    }

    public ResponseEntity<?> updateEvent(Event event) {
        Event eventFromDB = findEventByIdBasic(event.getId());
        if (eventFromDB.getId() != null) {
            if (event.getTitle() != null) {
                eventFromDB.setTitle(event.getTitle());
            }
            if (event.getDescription() != null) {
                eventFromDB.setDescription(event.getDescription());
            }
            if (event.getTotalEventSum() != null) {
                eventFromDB.setTotalEventSum(event.getTotalEventSum());
            }
            if (event.getEventUserList() != null) {
                eventFromDB.setEventUserList(event.getEventUserList());
            }
            if (event.getEventManager() != null) {
                eventFromDB.setEventManager(event.getEventManager());
            }
            Event savedEvent = eventRepository.save(eventFromDB);
            divisionOfExpenseService.calculateEvent(savedEvent);
            return new ResponseEntity<Event>(savedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateEventByPrincipal(Event event, String username) {
        if (!username.equals(eventRepository.findEventManagerUsernameById(event.getId()))) {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
        return updateEvent(event);
    }

    public ResponseEntity<?> updateEventByEventDtoForEditPageByPrincipal(EventDtoForEditPage eventDtoForEditPage, String username) {
        ResponseEntity<?> responseEntity;
        if (!username.equals(eventRepository.findEventManagerUsernameById(eventDtoForEditPage.getId()))) {
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.NO_CONTENT);
        }
        Event eventFromDB = findEventByIdBasic(eventDtoForEditPage.getId());
        List<User> userList = null;
        if (!eventDtoForEditPage.getEventUserList().isEmpty()) {
            userList = new ArrayList<>();
            for (String eventUserUsername : eventDtoForEditPage.getEventUserList()
            ) {
                User user = userService.findByUsername(eventUserUsername).orElse(null);
                if (user == null) {
                    return new ResponseEntity<String>("user with nickname " + eventUserUsername + " not found", HttpStatus.NO_CONTENT);
                }
                userList.add(user);
            }
        }
        Event event = mappingEventUtils.mapEventDtoForEditPageToEvent(eventFromDB, eventDtoForEditPage, userList);
        responseEntity = updateEvent(event);
        return responseEntity;
    }


    public void deleteEventByPrincipal(Long id, String username) {
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

    public List<EventDto> findEventsByParticipantUsername(
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
//        User user = userRepository.findByUsername(username).get();
        User user = userService.findByUsernameBasic(username);
        Event event = findEventByIdBasic(eventId);
        event.getEventUserList().add(user);
        return updateEvent(event);
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
//            e.printStackTrace();
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
