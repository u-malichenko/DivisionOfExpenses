package ru.division.of.expenses.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Expense extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;

    @Column
    private Calendar expenseDate;

    @Column
    private String comment;

    @Column
    private String billPhoto;

    @Column
    private BigDecimal totalExpenseSum;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "expense")
    private List<DirectPayer> directPayersList = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "expense")
    private List<PartitialPayer> partitialPayersList = new ArrayList<>();

    @ManyToOne
    private Event event;

}
