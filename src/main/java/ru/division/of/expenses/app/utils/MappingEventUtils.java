package ru.division.of.expenses.app.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.models.Event;
import ru.division.of.expenses.app.services.EventService;

@Component
public class MappingEventUtils {


    public EventDto mapToEventDto(Event event){

        // если нужен пустой объект, то блок "if" не нужен.
        if(event.getId() == null){
            return null;
        }
        ////////////////////////////////////
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setEventDateTime(event.getEventDateTime());
        eventDto.setTotalEventSum(event.getTotalEventSum());
        eventDto.setUsername(event.getEventManager().getUsername());
        return eventDto;
    }
}
