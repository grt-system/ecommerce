package com.imd.ecommerce.model;

import lombok.Data;

import java.sql.Time;

@Data
public class Fail {

    private String type;
    private Double probability;
    private Time duration;
}
