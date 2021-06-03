package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "direct_payer")
public class DirectPayer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private BigDecimal summa;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

}
