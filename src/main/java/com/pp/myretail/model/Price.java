package com.pp.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties("id")
public class Price {

    @Id
    public String id;
    private double value;
    private String currency_code;

}
