package com.app.tradeServer.repository;

import com.app.tradeServer.model.User;
import com.app.tradeServer.model.UserFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserFundsRepository extends JpaRepository<UserFunds,Long>{
    UserFunds findByUserId(Long userId);
}
