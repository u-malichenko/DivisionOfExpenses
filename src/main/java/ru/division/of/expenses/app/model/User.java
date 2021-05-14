package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "user_entity")
@Data
public class User extends AbstractEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}
