package ru.division.of.expenses.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.Event;

@NoArgsConstructor
@Data
public class EventDto {

    private Long id;
    private String name;

    public EventDto(Event event){
        this.id = event.getId();
        this.name = event.getName();
    }
}
