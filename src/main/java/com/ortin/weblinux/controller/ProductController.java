package com.ortin.weblinux.controller;

import com.ortin.weblinux.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductController() {
        addTestProducts();
    }

    private void addTestProducts() {
        Product p1 = new Product();
        p1.setId(counter.incrementAndGet());
        p1.setName("Test Product 1");
        p1.setDescription("Description 1");
        p1.setPrice(10.0);
        products.add(p1);

        Product p2 = new Product();
        p2.setId(counter.incrementAndGet());
        p2.setName("Test Product 2");
        p2.setDescription("Description 2");
        p2.setPrice(20.0);
        products.add(p2);

        Product p3 = new Product();
        p3.setId(counter.incrementAndGet());
        p3.setName("Test Product 3");
        p3.setDescription("Description 3");
        p3.setPrice(30.0);
        products.add(p3);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setId(counter.incrementAndGet());
        products.add(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean removed = products.removeIf(product -> product.getId().equals(id));

        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
} 