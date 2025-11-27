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

    public List<String> getAllCategories() {
        RestTemplate restTemplate = new RestTemplate();
        String[] categories = restTemplate.getForObject(API_URL + "/categories", String[].class);
        return Arrays.asList(categories);
    }

    public Product getProductById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL + "/" + id, Product.class);
    }

    public Product addProduct(Product product) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(API_URL, product, Product.class);
    }

    public void deleteProduct(int id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(API_URL + "/" + id);
    }
}
