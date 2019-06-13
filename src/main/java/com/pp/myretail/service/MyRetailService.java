package com.pp.myretail.service;

import com.pp.myretail.exceptions.MongoUnavailableException;
import com.pp.myretail.exceptions.PriceNotFoundException;
import com.pp.myretail.exceptions.ProductNotFoundException;
import com.pp.myretail.model.Price;
import com.pp.myretail.model.Product;
import com.pp.myretail.repository.PriceRepository;
import com.pp.myretail.repository.RedskyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyRetailService {

    private final RedskyRepository redskyRepository;
    private final PriceRepository priceRepository;

    @PostConstruct
    public void setup() {
        priceRepository.deleteAll();

        Price price1 = new Price();
        price1.setId("13860428");
        price1.setValue(13.49);
        price1.setCurrency_code("USD");

        Price price2 = new Price();
        price2.setId("13860429");
        price2.setValue(16.49);
        price2.setCurrency_code("USD");

        Price price3 = new Price();
        price3.setId("13860430");
        price3.setValue(189.24);
        price3.setCurrency_code("USD");

        Price price4 = new Price();
        price4.setId("13860431");
        price4.setValue(14.49);
        price4.setCurrency_code("USD");

        Price price5 = new Price();
        price5.setId("13860431");
        price5.setValue(14.49);
        price5.setCurrency_code("YEN");

        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);
        priceRepository.save(price5);

        log.info("Checking Price {}", priceRepository.findById("13860428"));
        log.info("Checking Price {}", priceRepository.findById("13860429"));
        log.info("Checking Price {}", priceRepository.findById("13860430"));
        log.info("Checking Price {}", priceRepository.findById("13860431"));

    }

    public Product getProduct(String id) throws ProductNotFoundException, PriceNotFoundException, MongoUnavailableException {

        Product product = this.redskyRepository.getProductFromRedsky(id);
        Optional<Price> optionalPrice;

        try {
            optionalPrice = this.priceRepository.findById(id);
        } catch (Exception e) {
            throw new MongoUnavailableException();
        }

        if (optionalPrice.isPresent()) {
            product.setCurrent_price(optionalPrice.get());
        } else {
            throw new PriceNotFoundException();
        }

        return product;
    }

    public Product updateProduct(Product product) throws MongoUnavailableException {

        Price price = product.getCurrent_price();
        log.info("Updating price for {}", price.getId());

        try {
             this.priceRepository.save(price);
        } catch (Exception e) {
            throw new MongoUnavailableException();
        }

        return product;
    }

}
