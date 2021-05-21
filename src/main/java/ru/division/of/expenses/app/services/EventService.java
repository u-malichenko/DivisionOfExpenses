package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.UserDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.models.User;
import ru.division.of.expenses.app.repositoryes.EventRepository;
import ru.division.of.expenses.app.utils.MappingEventUtils;
import ru.division.of.expenses.app.utils.MappingUserUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MappingEventUtils mappingEventUtils;
    private final MappingUserUtils mappingUserUtils;

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

    public Page<User> findEventUserlistById(
            Long id,
            int page,
            int size
    ){
        return eventRepository.findEventUserlistById(id, PageRequest.of(page, size));
    }

    public Page<UserDto> findEventUserDtolistById(
            Long id,
            int page,
            int size
    ){
        return eventRepository.findEventUserlistById(id, PageRequest.of(page, size)).map(mappingUserUtils::mapToUserDto);
    }

    public String findUsernameEventManagerById(Long id){
        return eventRepository.findUsernameEventManagerById(id);
    }



}
