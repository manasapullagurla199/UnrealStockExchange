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

    @Autowired
    private UserOrdersRepository userOrdersRepository;

    @Autowired
    private UserFundsService userFundsService;

    public void placeBuyOrder(UserOrders userOrder) {
        UserFunds userFunds = this.userFundsService.getUserFundsByUserId(userOrder.getUserId());
        double userAvailAmount = userFunds.getAmount();
        //buy
        double userReqAmount = userOrder.getStockId() * userOrder.getQuantity(); // TODO: multiply by price
        if (userReqAmount <= userAvailAmount) {
            userAvailAmount -= userReqAmount;
            userFunds.setAmount(userAvailAmount);
            userOrdersRepository.save(userOrder);
        }
    }

    public void placeSellOrder(UserOrders userOrder) {
        //sell
        List<UserStocks> userStocks=UserStocksService.getUserStocksByUserId(userOrder.getUserId());
//        UserFunds userFunds = this.userFundsService.getUserFundsByUserId(userOrder.getUserId());
        for(UserStocks stock: userStocks) {
            if (stock.getStockId().equals(userOrder.getStockId())) {
                if (stock.getQuantity() >= userOrder.getQuantity()) {
//                    double userAvailFunds=user.getAmount();
//                    UserFunds.setAmount(-(stock.getStockId()*userOr.getQuantity())); // TODO: hold stocks
                    userOrdersRepository.save(userOrder);
                }
            }
        }
    }
}
