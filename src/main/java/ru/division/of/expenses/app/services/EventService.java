package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundException;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.repositoryes.EventRepository;
import ru.division.of.expenses.app.utils.EmptyJsonResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

//    public EventDto findEventById(Long id)
////            throws EventNotFoundException
//    {
//
////        Event event = eventRepository.findById(id)
////                .orElseThrow(
////                        () -> new EventNotFoundException("Event: " + id + " not found.")
////                );
////        return new EventDto(event);
//        Event event = findEventByIdBasic(id);
//        if(event == null){
//            return new EventDto();
//        }
//        return new EventDto(event);
//
//    }

//    public EventDto findEventById(Long id) {
//
//        Event event = findEventByIdBasic(id);
////        if(event.getId() == null){
////            return new EventDto();
////        }
//        return new EventDto(event);
//
//    }
    public ResponseEntity<?> findEventById(Long id) {
        EventDto eventDto = new EventDto(findEventByIdBasic(id));
        if(eventDto.getId() != null){
            return new ResponseEntity<EventDto>(eventDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<EmptyJsonResponse>(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
    private Event findEventByIdBasic(Long id){
        Event event = new Event();
        try {
            event = eventRepository.findById(id)
                    .orElseThrow(
                            () -> new EventNotFoundException("Event: " + id + " not found.")
                    );
        }catch (EventNotFoundException e){
//            e.printStackTrace();
            System.out.println(e);
        }
        return event;
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

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) throws EventNotFoundException {
        Event eventFromDB = eventRepository.findById(event.getId())
                .orElseThrow(
                        () -> new EventNotFoundException("Event: " + event.getId() + " not found.")
                );
        eventFromDB.setName(event.getName());
        eventFromDB.setDescription(event.getDescription());
        eventFromDB.setTotalEventSum(event.getTotalEventSum());

        return eventRepository.save(eventFromDB);
    }

    public void deleteEvent(Long id) {
        Event eventFromDB = findEventByIdBasic(id);
            eventRepository.delete(eventFromDB);
    }

    public List<EventDto> findEventsByUserId(
            Long id,
            int page,
            int size
    ) {
        Page<Event> events = eventRepository.findEventsByUserId(id, PageRequest.of(page - 1, size));
        return events
                .stream()
                .map(EventDto::new)
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

//    private Event findEventByIdBasic(Long id){
//        Event event = new Event();
//        try {
//            event = eventRepository.findById(id)
//                    .orElseThrow(
//                            () -> new EventNotFoundException("Event: " + id + " not found.")
//                    );
//        }catch (EventNotFoundException e){
////            e.printStackTrace();
//            System.out.println(e);
//        }
//        return event;
//    }
}
