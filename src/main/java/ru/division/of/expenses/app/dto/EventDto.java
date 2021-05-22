package ru.division.of.expenses.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.Event;

import java.math.BigDecimal;
import java.util.Calendar;

//@NoArgsConstructor
@Data
public class EventDto {

    private Long id;
    private String name;
    private Calendar eventDateTime;
    private String username;
    private BigDecimal totalEventSum;

//    public EventDto(Event event){
//        this.id = event.getId();
//        this.name = event.getName();
//    }
}
