package ru.division.of.expenses.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.division.of.expenses.app.models.Event;

import java.math.BigDecimal;
import java.util.Calendar;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {

    private Long id;
    private String name;
    private Calendar eventDateTime;
    private String managerUsername;
    private BigDecimal totalEventSum;

    public EventDto(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventDateTime = event.getEventDateTime();
//        this.username = event.getName();
        if(this.id != null){
        this.managerUsername = event.getEventManager().getUsername();}
        this.totalEventSum = event.getTotalEventSum();
    }
}
