package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Order extends AbstractEntity {

    @ManyToOne
    @JoinTable(
            name = "users_orders",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private User buyer;

    @Column
    private Date orderDate;

    @Column
    private String comment;

    @Column
    private String billPhoto;

    @Column
    private BigDecimal totalOrderSum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "direct_payers_orders",
            joinColumns = @JoinColumn(name = "direct_payer_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Collection<DirectPayer> directPayersList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "partitial_payers_orders",
            joinColumns = @JoinColumn(name = "partitial_payer_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Collection<DirectPayer> partitialPayersList = new ArrayList<>();

}
