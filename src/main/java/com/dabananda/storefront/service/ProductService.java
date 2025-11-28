package com.dabananda.storefront.service;

import com.dabananda.storefront.dto.PaginatedResponse;
import com.dabananda.storefront.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final String API_URL = "https://fakestoreapi.com/products";

//    public List<Product> getAllProducts() {
//        RestTemplate restTemplate = new RestTemplate();
//        Product[] products = restTemplate.getForObject(API_URL, Product[].class);
//        return Arrays.asList(products);
//    }

    public PaginatedResponse<Product> getAllProducts(int page, int size, String search) {
        RestTemplate restTemplate = new RestTemplate();
        Product[] productsArray = restTemplate.getForObject(API_URL,  Product[].class);
        List<Product> allProducts = productsArray != null ? Arrays.asList(productsArray) : Collections.emptyList();

        if (search != null && !search.isEmpty()) {
            String lowerCaseSearch = search.toLowerCase();
            allProducts = allProducts.stream().filter(p -> p.getTitle().toLowerCase().contains(lowerCaseSearch) || p.getCategory().toLowerCase().contains(lowerCaseSearch) || p.getDescription().toLowerCase().contains(lowerCaseSearch)).collect(Collectors.toList());
        }

        int totalElements = allProducts.size();
        int totalPages = totalElements / size;

        int start = page * size;
        int end = Math.min(start + size, totalElements);

        List<Product> pageContents;
        if (start >= totalElements) {
            pageContents = Collections.emptyList();
        } else {
            pageContents = allProducts.subList(start, end);
        }

        return new PaginatedResponse<>(pageContents, page, size, totalElements, totalPages);
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

    public Product updateProduct(int id, Product product) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(API_URL + "/" + id, product);
        product.setId(id);
        return product;
    }
}
