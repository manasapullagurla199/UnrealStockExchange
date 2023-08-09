package com.app.tradeServer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "\"User\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @OneToOne
    private UserFunds funds;

    @OneToMany(mappedBy = "user")
    private List<UserOrders> userOrders;

    @OneToMany(mappedBy = "user")
    private List<UserStocks> userStocks;
}
