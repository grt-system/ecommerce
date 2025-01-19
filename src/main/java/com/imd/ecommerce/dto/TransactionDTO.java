package com.imd.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private long transactionId;

    public long getTransactionIdId() {
        return transactionId;
    }

    public void setTransactionIdId(long transactionId) {
        this.transactionId = transactionId;
    }
}
