package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class PartitialPayer extends AbstractEntity {

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Double coefficient;

    @ManyToOne
//    @JoinColumn(name = "order_id")
    private Orders order;

}
