package ru.division.of.expenses.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.division.of.expenses.app.dto.EventDto;
import ru.division.of.expenses.app.dto.MenuElementEvent;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuElementEventService {
    private final EventService eventService;

    public List<MenuElementEvent> findAllMenuElementEvents(
            Long userId,
            int page,
            int size
    ) {
        List<MenuElementEvent> menuElementEventList = new ArrayList<>();
        List<EventDto> eventDtoList = eventService.findEventsByUserId(userId, page, size);
        eventDtoList.forEach(
                eventDto -> menuElementEventList.add(new MenuElementEvent(
                        eventDto.getName(),
                        eventDto.getEventDateTime(),
                        eventDto.getAmountOfExpense(),
                        eventDto.getEventUserList(),
                        eventDto.getTotalEventSum()
                ))
        );
        return menuElementEventList;
    }
}
