package com.pp.myretail;

import com.pp.myretail.model.Price;
import com.pp.myretail.model.Product;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MyRetailApplication.class)
public class MyRetailApplicationIntegrationTest {

    private static MongodExecutable mongodExecutable;
    private RestTemplate restTemplate;
    private final String url = "http://localhost:8080/products/";

    public MyRetailApplicationIntegrationTest() {
        restTemplate = new RestTemplate();
    }

    @BeforeClass
    public static void setup() throws Exception {

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net("127.0.0.1", 27017, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @AfterClass
    public static void cleanup() {
        mongodExecutable.stop();
    }


    @Test
    public void getProduct_UpdateProduct_GetUpdatedProduct() {

        Product originalProduct = new Product();
        originalProduct.setId(13860428);
        originalProduct.setName("The Big Lebowski (Blu-ray)");
        Price oldPrice = new Price();
        oldPrice.setValue(13.49);
        oldPrice.setCurrency_code("USD");
        originalProduct.setCurrent_price(oldPrice);


        ResponseEntity<Product> responseEntity;
        responseEntity = restTemplate.getForEntity(url + 13860428, Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(originalProduct, responseEntity.getBody());

        Product product = new Product();
        product.setId(13860428);
        product.setName("The Big Lebowski (Blu-ray)");
        Price price = new Price();
        price.setValue(15.49);
        price.setCurrency_code("YEN");
        product.setCurrent_price(price);

        HttpEntity<Product> entity = new HttpEntity<>(product);

        responseEntity = restTemplate.exchange(url + 13860428, HttpMethod.PUT,  entity, Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());

        responseEntity = restTemplate.getForEntity(url + 13860428, Product.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
    }
}
