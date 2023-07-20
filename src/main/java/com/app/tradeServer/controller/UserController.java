package com.app.tradeServer.controller;

import com.app.tradeServer.model.*;
import com.app.tradeServer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyOrder(@RequestBody UserOrders userOrders, Long userId){
        userOrdersService.placeOrder(userOrders);
        return ResponseEntity.ok("Buy order placed successfully");
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellOrder(@RequestBody UserOrders userOrders, User user){
        userOrdersService.placeOrder(userOrders);
        return ResponseEntity.ok("Sell order placed successfully");
    }

    @PostMapping("/add-funds")
    public ResponseEntity<String> addFunds(@RequestBody UserFunds userFunds){
        userFundsService.updateUserFunds(userFunds);
        return ResponseEntity.ok("Funds added successfully");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }

}
