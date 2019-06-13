package com.pp.myretail.controller;


import com.pp.myretail.exceptions.BadRequestException;
import com.pp.myretail.exceptions.MongoUnavailableException;
import com.pp.myretail.exceptions.PriceNotFoundException;
import com.pp.myretail.exceptions.ProductNotFoundException;
import com.pp.myretail.model.Product;
import com.pp.myretail.service.MyRetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyRetailController {

    private final MyRetailService myRetailService;

    @GetMapping("/products/{id}")
    public Object getProduct(@PathVariable("id") String id) throws ProductNotFoundException, PriceNotFoundException, MongoUnavailableException, BadRequestException {

        if (id == null || id.isEmpty()) {
            throw new BadRequestException();
        }

        return myRetailService.getProduct(id);
    }

    @PutMapping("/products/{id}")
    public Object updatePrice(@RequestBody Product product, @PathVariable("id") String id) throws MongoUnavailableException, BadRequestException {


        try {
            if (product == null || product.getId() != Integer.parseInt(id)) {
                throw new BadRequestException();
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException();
        }

        product.getCurrent_price().setId(id);
        return myRetailService.updateProduct(product);
    }
}
