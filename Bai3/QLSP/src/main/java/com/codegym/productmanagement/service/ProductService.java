package com.codegym.productmanagement.service;

import com.codegym.productmanagement.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService implements IProductService {
    private static final Map<Integer, Product> products = new HashMap<>();
    private static final AtomicInteger autoId = new AtomicInteger(5);

    static {
        products.put(1, new Product(1, "iPhone 15 Pro Max", 1299.0, "Titanium Blue, 256GB, A17 Pro chip", "Apple"));
        products.put(2, new Product(2, "Samsung Galaxy S24 Ultra", 1199.0, "Titanium Gray, 256GB, Galaxy AI", "Samsung"));
        products.put(3, new Product(3, "MacBook Pro 14 M3", 1599.0, "Space Black, 18GB RAM, 512GB SSD", "Apple"));
        products.put(4, new Product(4, "Sony WH-1000XM5", 399.0, "Noise Canceling Wireless Headphones", "Sony"));
        products.put(5, new Product(5, "Dell XPS 15", 1499.0, "Intel Core i7, 16GB RAM, OLED 3.5K", "Dell"));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void save(Product product) {
        if (product.getId() == 0) {
            product.setId(autoId.incrementAndGet());
        }
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(int id) {
        return products.get(id);
    }

    @Override
    public void update(int id, Product product) {
        product.setId(id);
        products.put(id, product);
    }

    @Override
    public void remove(int id) {
        products.remove(id);
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        String keyword = name.trim().toLowerCase();
        for (Product product : products.values()) {
            if (product.getName() != null && product.getName().toLowerCase().contains(keyword)) {
                result.add(product);
            }
        }
        return result;
    }
}
