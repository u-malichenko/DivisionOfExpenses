package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class ShoppingListItem extends AbstractEntity {

    @Column
    private String name;

    @Column
    private String quantity;

}
