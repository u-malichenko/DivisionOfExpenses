package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.exceptions_handling.EventNotFoundExcpetion;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.repositoryes.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventDto findEventById(Long id) throws EventNotFoundExcpetion {
        Event event = eventRepository.findById(id)
                .orElseThrow(
                        () -> new EventNotFoundExcpetion("Event: " + id + " not found.")
                );
        return new EventDto(event);
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

    public Event updateEvent(Event event) throws EventNotFoundExcpetion {
        Event eventFromDB = eventRepository.findById(event.getId())
                .orElseThrow(
                        () -> new EventNotFoundExcpetion("Event: " + event.getId() + " not found.")
                );
        eventFromDB.setName(event.getName());
        eventFromDB.setDescription(event.getDescription());
        eventFromDB.setTotalEventSum(event.getTotalEventSum());

        return eventRepository.save(eventFromDB);
    }

    public void deleteEvent(Long id) throws EventNotFoundExcpetion {
        Event eventFromDB = eventRepository.findById(id)
                .orElseThrow(
                        () -> new EventNotFoundExcpetion("Event: " + id + " not found.")
                );
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
}
