package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.repositoryes.EventRepository;
import ru.division.of.expenses.app.utils.MappingEventUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MappingEventUtils mappingEventUtils;

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public EventDto findEventDtoById(Long id){
        return mappingEventUtils.mapToEventDto(eventRepository.findById(id).orElse(new Event()));
    }

    public Page<Event> findAll(
            int page,
            int size
    ) {
        return eventRepository.findAll(PageRequest.of(page, size));
    }

    public Page<EventDto> findAllEventDto(
            int page,
            int size
    ) {
        return eventRepository.findAll(PageRequest.of(page, size)).map(mappingEventUtils::mapToEventDto);
    }


}
