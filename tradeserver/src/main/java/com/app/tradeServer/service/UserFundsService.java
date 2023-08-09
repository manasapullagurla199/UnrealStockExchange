package com.app.tradeServer.service;

import com.app.tradeServer.model.UserFunds;
import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserFundsService {
    private UserRepository userRepository;
    private UserFundsRepository userFundsRepository;

    @Autowired
    public UserFundsService(UserFundsRepository userFundsRepository, UserRepository userRepository) {
        this.userFundsRepository = userFundsRepository;
        this.userRepository = userRepository;
    }

    // Method to update user funds
    public UserFunds updateUserFunds(UserFunds userFunds) {
        if (userRepository.existsById(userFunds.getUser().getId())) {
            UserFunds existFunds = userFundsRepository.findByUserId(userFunds.getUser().getId());
            if (existFunds != null) {
                // Add the new amount to the existing user funds
                existFunds.setAmount(existFunds.getAmount() + userFunds.getAmount());
                return userFundsRepository.save(existFunds);
            }
        }
        // Save the new user funds if no existing funds found
        return userFundsRepository.save(userFunds);
    }


    //    public UserFunds getUserFundsByUserId(Long userId){
//        UserFunds userFunds =userFundsRepository.findByUserId(userId);
//        return userFunds;
//    }
}
