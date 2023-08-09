package com.app.tradeServer.repository;

import com.app.tradeServer.model.User;
import com.app.tradeServer.model.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders,Long> {
    UserOrders getOrderByOrderId(Long userId);
}
