package com.app.tradeServer.repository;

import com.app.tradeServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User getUserById(Long id);
}
