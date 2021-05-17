package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "direct_payer")
public class DirectPayer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Double summa;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Expense order;

}
