package ru.division.of.expenses.app.models;

import lombok.Data;
import lombok.ToString;

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
