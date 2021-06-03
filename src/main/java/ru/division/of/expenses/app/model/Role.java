package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "roles")
public class Role extends AbstractEntity{

    @Column(name = "name")
    private String name;

}
