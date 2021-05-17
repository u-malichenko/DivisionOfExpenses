package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Expense extends AbstractEntity {

    @ManyToOne
    private User buyer;

    @Column
    private Date orderDate;

    @Column
    private String comment;

    @Column
    private String billPhoto;

    @Column
    private BigDecimal totalOrderSum;

    @OneToMany(mappedBy = "order")
    private List<DirectPayer> directPayersList = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<PartitialPayer> partitialPayersList = new ArrayList<>();

}
