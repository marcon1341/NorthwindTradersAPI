package com.Pluralsight1.NorthwindTradersAPI.dao;

import com.Pluralsight1.NorthwindTradersAPI.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    Product getById(int id);
    Product insert(Product product);
    void update(int id, Product product);
}
