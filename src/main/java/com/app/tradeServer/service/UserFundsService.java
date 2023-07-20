package com.app.tradeServer.service;

import com.app.tradeServer.model.UserFunds;
import com.app.tradeServer.repository.UserFundsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
public class UserFundsService {
    private UserFundsRepository userFundsRepository;

    @Autowired
    public UserFundsService(UserFundsRepository userFundsRepository) {
        this.userFundsRepository = userFundsRepository;
    }
    public void updateUserFunds(UserFunds userFunds){

        userFundsRepository.save(userFunds);
    }

    public UserFunds getUserFundsByUserId(Long userId){
        UserFunds userFunds =userFundsRepository.findByUserId(userId);
        return userFunds;
    }
}
