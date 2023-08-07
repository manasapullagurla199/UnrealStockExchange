package com.app.tradeServer.controller;

import com.app.tradeServer.model.*;
import com.app.tradeServer.repository.UserRepository;
import com.app.tradeServer.service.UserFundsService;
import com.app.tradeServer.service.UserOrdersService;
import com.app.tradeServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrdersService userOrdersService;
    @Autowired
    private UserFundsService userFundsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyOrder(@RequestBody OrderRequest orderRequest) {
        User user = userRepository.getReferenceById(orderRequest.getUser_id());
        UserOrders userOrders = UserOrders.builder()
                .user(user)
                .stockId(orderRequest.getStockId())
                .quantity(orderRequest.getQuantity())
                .orderType(orderRequest.getOrderType())
                .build();
        userOrdersService.placeBuyOrder(userOrders);
        return ResponseEntity.ok("Buy order placed successfully");
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellOrder(@RequestBody OrderRequest orderRequest) {
        User user = userRepository.getReferenceById(orderRequest.getUser_id());
        UserOrders userOrders = UserOrders.builder()
                .user(user)
                .stockId(orderRequest.getStockId())
                .quantity(orderRequest.getQuantity())
                .orderType(orderRequest.getOrderType())
                .build();
        userOrdersService.placeSellOrder(userOrders);
        return ResponseEntity.ok("Sell order placed successfully");
    }

    @PostMapping("/add-funds")
    public ResponseEntity<String> addFunds(@RequestBody UserFundsRequest fundsRequest) {
        User user = userRepository.getReferenceById(fundsRequest.getUser_id());
        UserFunds funds = UserFunds.builder()
                .user(user)
                .amount(fundsRequest.getAmount())
                .build();
        funds = userFundsService.updateUserFunds(funds);
        user.setFunds(funds);
        userService.updateUser(user);
        return ResponseEntity.ok("Funds added successfully");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

}
