package ru.division.of.expenses.app.models;

import lombok.Data;
import lombok.ToString;

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
