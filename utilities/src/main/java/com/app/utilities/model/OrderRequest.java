package com.app.utilities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    Long user_id;
    Long stockId;
    double quantity;
    String orderType;
    double targetPrice;
}
