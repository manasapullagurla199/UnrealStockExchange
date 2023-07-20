package com.app.tradeServer.service;

import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserOrdersRepository;
import com.app.tradeServer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserOrdersService {
    private final UserOrdersRepository userOrdersRepository;

    @Autowired
    public UserOrdersService(UserOrdersRepository userOrdersRepository) {
        this.userOrdersRepository = userOrdersRepository;
    }
    public void placeBuyOrder(UserOrders userOrder) {
        UserFunds userFunds = UserFundsService.getUserFundsByUserId(userOrder.getUserId());
        double userAvailAmount = userFunds.getAmount();
        //buy
        double userReqAmount = userOrder.getStockId().getPrice() * userOrder.getQuantity()
        if (userReqAmount <= userAvailAmount) {
            userAvailAmount -= userReqAmount;
            userFunds.setAmount(userAvailAmount);
            userOrdersRepository.save(userOrder);
        }
    }
        //sell
        List<UserStocks> userAvailStocks=UserStocksService.getUserStocksByUserId(userOrder.getUserId());
        for(UserStocks stock: UserStocks) {
            if (stock.getStockId().equals(UserOrders.getStockId())) {
                if (stock.getQuantity() >= UserOrders.getQuantity()) {
                    double userAvailFunds=UserFunds.getAmount();
                    UserFunds.setAmount(userAvailFunds-(stock.getStockId().getPrice()*UserOrders.getQuantity()));
                    userOrdersRepository.save(userOrder);
                }
            }
        }
    }
}
