package com.app.tradeServer.repository;

import com.app.tradeServer.model.UserStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStocksRepository extends JpaRepository<UserStocks,Long> {
    public List<UserStocks> getUserStocksByUserId(Long userId);
}
