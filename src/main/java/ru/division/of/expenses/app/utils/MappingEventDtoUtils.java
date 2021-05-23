package ru.division.of.expenses.app.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.services.UserService;

@Component
@RequiredArgsConstructor
public class MappingEventDtoUtils {

    private final UserService userService;

    public Event mapToEvent(EventDto eventDto){

        Event event = new Event();
//        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setEventDateTime(eventDto.getEventDateTime());
        event.setTotalEventSum(eventDto.getTotalEventSum());
        event.setDescription(eventDto.getDescription());
        event.setEventManager(userService.findByUsername(eventDto.getManagerUsername()).get());
        return event;

    }
}
