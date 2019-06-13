package com.pp.myretail.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pp.myretail.exceptions.ProductNotFoundException;
import com.pp.myretail.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedskyRepository {

    @Value("${redsky.url}")
    private String redskyUrl;

    private final RestTemplate restTemplate;

    public Product getProductFromRedsky(String id) throws ProductNotFoundException {

        String url = redskyUrl + id;

        log.info("GET {}", url);

        ResponseEntity<String> response;

        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            throw new ProductNotFoundException();
        }

        log.info("RESPONSE {}", response);

        JsonElement element = new JsonParser().parse(response.getBody());
        JsonObject obj = element.getAsJsonObject();
        String title = obj.getAsJsonObject("product")
                .getAsJsonObject("item")
                .getAsJsonObject("product_description")
                .get("title")
                .getAsString();

        Product product = new Product();
        product.setId(Integer.parseInt(id));
        product.setName(title);

        log.debug("Title {}", title);

        return product;
    }

    // For testing purposes
    public void setRedskyUrl(String redskyUrl) {
        this.redskyUrl = redskyUrl;
    }
}


