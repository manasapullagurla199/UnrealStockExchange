package com.app.tradeServer.controller;

import com.app.tradeServer.model.*;
import com.app.tradeServer.repository.UserRepository;
import com.app.tradeServer.service.StockSymbols;
import com.app.tradeServer.service.UserFundsService;
import com.app.tradeServer.service.UserOrdersService;
import com.app.tradeServer.service.UserService;
import com.app.utilities.model.OrderRequest;
import com.app.utilities.model.UserFundsRequest;
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

    @Autowired
    private StockSymbols stockSymbols;

    // Endpoint to create a new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    // Endpoint to place a buy order
    @PostMapping("/buy")
    public ResponseEntity<String> buyOrder(@RequestBody OrderRequest orderRequest) {
        // Get a reference to the user based on user ID
        User user = userRepository.getReferenceById(orderRequest.getUser_id());

        // Get the stock symbol based on stock ID
        String stockSymbol = stockSymbols.getStockSymbols(orderRequest.getStockId());

        // Create a new UserOrders object for buy order
        UserOrders userOrders = UserOrders.builder()
                .user(user)
                .stockId(orderRequest.getStockId())
                .stockSymbol(stockSymbol)
                .quantity(orderRequest.getQuantity())
                .orderType(orderRequest.getOrderType())
                .targetPrice(orderRequest.getTargetPrice())
                .build();

        // Place the buy order
        userOrdersService.placeBuyOrder(userOrders);
        return ResponseEntity.ok("Buy order placed successfully");
    }

    // Endpoint to place a sell order
    @PostMapping("/sell")
    public ResponseEntity<String> sellOrder(@RequestBody OrderRequest orderRequest) {
        // Get a reference to the user based on user ID
        User user = userRepository.getReferenceById(orderRequest.getUser_id());

        // Create a new UserOrders object for sell order
        UserOrders userOrders = UserOrders.builder()
                .user(user)
                .stockId(orderRequest.getStockId())
                .quantity(orderRequest.getQuantity())
                .orderType(orderRequest.getOrderType())
                .targetPrice(orderRequest.getTargetPrice())
                .build();

        // Place the sell order
        userOrdersService.placeSellOrder(userOrders);
        return ResponseEntity.ok("Sell order placed successfully");
    }

    // Endpoint to add funds to user's account
    @PostMapping("/add-funds")
    public ResponseEntity<String> addFunds(@RequestBody UserFundsRequest fundsRequest) {
        // Get a reference to the user based on user ID
        User user = userRepository.getReferenceById(fundsRequest.getUser_id());

        // Create a new UserFunds object for adding funds
        UserFunds funds = UserFunds.builder()
                .user(user)
                .amount(fundsRequest.getAmount())
                .build();

        // Update user funds
        funds = userFundsService.updateUserFunds(funds);
        user.setFunds(funds);
        userService.updateUser(user);
        return ResponseEntity.ok("Funds added successfully");
    }

    // Exception handler for handling exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
