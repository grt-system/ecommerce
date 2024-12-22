package com.imd.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class FidelityDTO implements Serializable {
    private long userId;
    private double bonus;

    public FidelityDTO(long userId, double bonus) {
        this.userId = userId;
        this.bonus = bonus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
