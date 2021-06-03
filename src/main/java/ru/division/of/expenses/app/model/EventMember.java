package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "event_member")
@Data
public class EventMember extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private BigDecimal saldo;

    @ManyToOne
    private Event event;

}
