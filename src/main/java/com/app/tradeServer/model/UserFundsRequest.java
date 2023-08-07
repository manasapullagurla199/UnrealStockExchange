package com.app.tradeServer.model;

import lombok.Data;

@Data
public class UserFundsRequest {
    Long user_id;
    Double amount;
}
