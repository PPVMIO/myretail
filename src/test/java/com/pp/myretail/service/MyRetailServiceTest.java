package com.pp.myretail.service;

import com.pp.myretail.exceptions.MongoUnavailableException;
import com.pp.myretail.exceptions.PriceNotFoundException;
import com.pp.myretail.model.Price;
import com.pp.myretail.model.Product;
import com.pp.myretail.repository.PriceRepository;
import com.pp.myretail.repository.RedskyRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyRetailServiceTest {

    private PriceRepository priceRepository;
    private RedskyRepository redskyRepository;
    private MyRetailService myRetailService;

    public MyRetailServiceTest() {
        priceRepository = mock(PriceRepository.class);
        redskyRepository = mock(RedskyRepository.class);
        myRetailService = new MyRetailService(redskyRepository, priceRepository);
    }

    @Test
    public void getProduct_Successfully() throws Exception {
        Product product = new Product();
        product.setId(1234);
        product.setName("My Product");


        Price price = new Price();
        price.setCurrency_code("USD");
        price.setValue(13.35);

        when(redskyRepository.getProductFromRedsky(anyString())).thenReturn(product);
        when(this.priceRepository.findById(anyString())).thenReturn(Optional.of(price));

        product.setCurrent_price(price);

        assertEquals(product, myRetailService.getProduct("abcd"));

    }

    @Test(expected = PriceNotFoundException.class)
    public void getProduct_Throws_Price_NotFound_When_FindById_Returns_Empty() throws Exception{

        Product product = new Product();
        product.setId(1234);

        when(redskyRepository.getProductFromRedsky(anyString())).thenReturn(product);
        when(this.priceRepository.findById(anyString())).thenReturn(Optional.empty());

        myRetailService.getProduct("abcd");
    }

}