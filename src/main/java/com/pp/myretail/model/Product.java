package com.pp.myretail.model;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private Price current_price;

}
