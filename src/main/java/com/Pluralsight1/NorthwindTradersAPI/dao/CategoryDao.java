package com.Pluralsight1.NorthwindTradersAPI.dao;

import com.Pluralsight1.NorthwindTradersAPI.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
    Category insert(Category category);
}
