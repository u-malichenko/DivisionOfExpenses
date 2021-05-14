package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class Role extends AbstractEntity{


    @Column(name = "name")
    private String name;

}
