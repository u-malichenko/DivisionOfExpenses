package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Data
public class Event extends AbstractEntity {

    @ManyToMany
    @JoinTable(
            name = "events_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Collection<User> eventUserList = new ArrayList<>();


    @OneToMany(mappedBy="event", cascade=CascadeType.ALL)
    private Collection<EventMember> eventMembers = new ArrayList<>();

    @Column
    private Calendar eventDateTime;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private Collection<Expense> expenseList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User eventManager;

    @Column
    private BigDecimal totalEventSum;

    @OneToOne
    private ShoppingList shoppingList;

}
