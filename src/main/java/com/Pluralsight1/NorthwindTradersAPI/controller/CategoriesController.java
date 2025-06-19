package com.Pluralsight1.NorthwindTradersAPI.controller;

import com.Pluralsight1.NorthwindTradersAPI.dao.CategoryDao;
import com.Pluralsight1.NorthwindTradersAPI.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoriesController {
    private CategoryDao categoryDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    //    private List<Category> getCategoryList(){
//        List<Category> categories = new ArrayList<>();
//        categories.add(new Category(10,"Tea"));
//        categories.add(new Category(20,"Coffee"));
//        categories.add(new Category(30,"NEW"));
//        return categories;
//    }
    @GetMapping("/categories")
    public List<Category> getCategories(@RequestParam(required = false) String name) {
        List<Category> categories = categoryDao.getAll();
        if (name != null && !name.isEmpty()) {
            List<Category> filtered = new ArrayList<>();
            for (Category category : categories) {
                if (category.getCategoryName().toLowerCase().contains(name.toLowerCase())) {
                    filtered.add(category);
                }
                return filtered;
            }
        }
        return categories;
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryDao.getById(id);
//        for (Category category : getCategoryList()){
//            if (category.getCategoryId() == id){
//                return category;
//            }
//        }
//        return null;
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDao.insert(category);
    }
}
