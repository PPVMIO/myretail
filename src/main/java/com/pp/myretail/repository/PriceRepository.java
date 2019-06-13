package com.pp.myretail.repository;

import com.pp.myretail.model.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, String> {}
