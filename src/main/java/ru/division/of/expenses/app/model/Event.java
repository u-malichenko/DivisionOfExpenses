package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Event extends AbstractEntity {

    @OneToOne
    ShoppingList shoppingList;
    @Column
    private Date eventDateTime;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal totalEventSum;
    @OneToMany
    private Collection<Role> roles = new ArrayList<>();

}
