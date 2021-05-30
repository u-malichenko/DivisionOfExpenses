package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.ExpenseDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundException;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.Expense;
import ru.division.of.expenses.app.repositoryes.EventRepository;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public ResponseEntity<?> findEventById(Long id) {
        EventDto eventDto = new EventDto(findEventByIdBasic(id));
        if (eventDto.getId() != null) {
            return new ResponseEntity<EventDto>(eventDto, HttpStatus.OK);
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


    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }

//    public EventDto saveEventDto(Event event){
//        return new EventDto(eventRepository.save(event));
//    }

    public ResponseEntity<?> updateEvent(Event event){
        Event eventFromDB = findEventByIdBasic(event.getId());
        if(eventFromDB.getId() != null){
            if(event.getName() != null){
            eventFromDB.setName(event.getName());}
            if(event.getDescription() != null){
            eventFromDB.setDescription(event.getDescription());}
            if (event.getTotalEventSum() != null){
            eventFromDB.setTotalEventSum(event.getTotalEventSum());}
            return new ResponseEntity<Event>(eventRepository.save(eventFromDB), HttpStatus.OK);
        }else{
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateEventByPrincipal(Event event, String username){
        if(!username.equals(eventRepository.findEventManagerUsernameById(event.getId()))){
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
        return updateEvent(event);
    }


    public void deleteEventByPrincipal(Long id, String username) {
        if(!username.equals(eventRepository.findEventManagerUsernameById(id))){
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
    ){
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
    ){
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
    ){
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
    ){
        Page<Event> events = eventRepository.findEventByParticipantId(id, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map(EventDto::new)
                .collect(Collectors.toList());
    }

    private Event findEventByIdBasic(Long id){
        Event event = new Event();
        try {
            event = eventRepository.findById(id)
                    .orElseThrow(
                            () -> new EventNotFoundException("Event: " + id + " not found.")
                    );
        }catch (EventNotFoundException e) {
//            e.printStackTrace();
            System.out.println(e);
        }
        return event;
    }

}
