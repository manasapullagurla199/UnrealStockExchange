package com.app.tradeServer.service;

import com.app.tradeServer.model.UserFunds;
import com.app.tradeServer.repository.UserFundsRepository;
import com.app.tradeServer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
@Slf4j
public class UserFundsService {
    private UserRepository userRepository;
    private UserFundsRepository userFundsRepository;

    @Autowired
    public UserFundsService(UserFundsRepository userFundsRepository,UserRepository userRepository) {
        this.userFundsRepository = userFundsRepository;
        this.userRepository = userRepository;
    }
    public UserFunds updateUserFunds(UserFunds userFunds){
        //TODO: check if the user is already present and then update the user funds.
        return userFundsRepository.save(userFunds);
    }

    public UserFunds getUserFundsByUserId(Long userId){
        UserFunds userFunds =userFundsRepository.findByUserId(userId);
        log.info("userId in UserFundsService:{}",userId);
        log.info("userFUnds: {}",userFunds);
        return userFunds;
    }
}
