package com.app.tradeServer.model;

import lombok.Data;

@Data
public class OrderRequest {
    Long user_id;
    Long stockId;
    Double quantity;
    String orderType;
}
