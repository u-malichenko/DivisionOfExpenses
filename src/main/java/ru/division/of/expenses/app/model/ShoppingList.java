package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class ShoppingList extends AbstractEntity {

    @Column
    private String comment;

    @OneToOne
    private Event event;

    @Column
    private Boolean enabledFlag;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shoppinglistitem_id")
    private Collection<ShoppingListItem> shoppingList = new ArrayList<>();

}
