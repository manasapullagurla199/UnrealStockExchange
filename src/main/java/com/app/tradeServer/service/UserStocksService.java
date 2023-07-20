package com.app.tradeServer.service;

import com.app.tradeServer.model.UserStocks;
import com.app.tradeServer.repository.UserStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserStocksService {
    private static UserStocksRepository userStocksRepository;
    @Autowired
    public UserStocksService(UserStocksRepository userStocksRepository){
        this.userStocksRepository=userStocksRepository;
    }

    public static List<UserStocks> getUserStocksByUserId(Long userId) {
        return userStocksRepository.getUserStocksById(userId);
        // Logic to fetch and return all user stocks for a specific user from the UserStockRepository
    }
}
