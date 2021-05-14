package ru.division.of.expenses.app.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PartitialPayer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Double coefficient;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
