package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role extends AbstractEntity{


    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = User.class)
    private List<User> userList;
}
