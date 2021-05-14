package ru.division.of.expenses.app.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
@Data
public class Order extends AbstractEntity {

    @OneToOne
    private User buyer;

    @Column
    private Date orderDate;

    @Column
    private String comment;

    @Column
    private String billPhoto;

    @Column
    private BigDecimal totalOrderSum;

    @ElementCollection
    @CollectionTable(name = "direct_payers_map",
            joinColumns = {@JoinColumn(name = "user_entity_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "user_entity_name")
    @Column(name = "direct_payers")
    private Map<User, Double> directPayersMap;

    @ElementCollection
    @CollectionTable(name = "partitial_payers_map",
            joinColumns = {@JoinColumn(name = "user_entity_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "user_entity_name")
    @Column(name = "partitial_payers")
    private Map<User, Double> partitialPayersMap;

}
