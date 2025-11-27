package com.dabananda.storefront.service;

import com.dabananda.storefront.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final String API_URL = "https://fakestoreapi.com/products";

    public List<Product> getAllProducts() {
        RestTemplate restTemplate = new RestTemplate();
        Product[] products = restTemplate.getForObject(API_URL, Product[].class);
        return Arrays.asList(products);
    }
}
