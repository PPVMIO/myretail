package com.pp.myretail.controller;

import com.pp.myretail.exceptions.BadRequestException;
import com.pp.myretail.model.Product;
import com.pp.myretail.service.MyRetailService;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MyRetailControllerTest {

    private MyRetailService myRetailService;
    private MyRetailController myRetailController;

    public MyRetailControllerTest() {
        myRetailService = mock(MyRetailService.class);
        myRetailController = new MyRetailController(myRetailService);
    }


    @Test(expected = BadRequestException.class)
    public void getProduct_Throws_Exception_When_Id_Is_Null() throws Exception{
        myRetailController.getProduct(null);
    }

    @Test(expected = BadRequestException.class)
    public void updateProduct_Throws_Exception_When_String_Id_Does_Not_Match() throws Exception {

        Product product = new Product();
        product.setName("Test Product");
        product.setId(1234);

        myRetailController.updatePrice(product, "abcd");

    }

    @Test(expected = BadRequestException.class)
    public void updateProduct_Throws_Exception_When_Number_Id_Does_Not_Match() throws Exception {

        Product product = new Product();
        product.setName("Test Product");
        product.setId(1234);

        myRetailController.updatePrice(product, "5678");

    }
}