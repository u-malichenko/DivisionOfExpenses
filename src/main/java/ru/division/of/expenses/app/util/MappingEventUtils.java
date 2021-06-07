package ru.division.of.expenses.app.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.EventDtoForEditPage;
import ru.division.of.expenses.app.model.Event;
import ru.division.of.expenses.app.model.User;

import java.util.List;

@Component
@AllArgsConstructor
public class MappingEventUtils {

    public EventDto mapToEventDto(Event event){

        // если нужен пустой объект, то блок "if" не нужен.
        if(event.getId() == null){
            return null;
        }
        ////////////////////////////////////
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setTotalEventSum(event.getTotalEventSum());
//        eventDto.setManagerUsername(event.getEventManager().getUsername());
        return eventDto;
    }

    public Event mapEventDtoForEditPageToEvent(Event event, EventDtoForEditPage eventDtoForEditPage, List<User> userList) {
        event.setTitle(eventDtoForEditPage.getTitle());
        event.setDescription(eventDtoForEditPage.getDescription());
        event.setEventUserList(userList);
        return event;

    }
}
