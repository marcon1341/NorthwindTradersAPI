package com.Pluralsight1.NorthwindTradersAPI.controller;

import com.Pluralsight1.NorthwindTradersAPI.dao.ProductDao;
import com.Pluralsight1.NorthwindTradersAPI.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {
    private ProductDao productDao;

    @Autowired
    public ProductsController(ProductDao productDao){
        this.productDao=productDao;
    }
//    private List<Product> getProductList() {
//        List<Product> products = new ArrayList<>();
//        products.add(new Product(1, "Chai", 10, 18.00));
//        products.add(new Product(2, "Pepper", 20, 19.00));
//        products.add(new Product(3, "Mix", 30, 21.50));
//        return products;
//    }

    @GetMapping("/products")
    public List<Product> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double unitPrice) {

        List<Product> products = productDao.getAll();
        List<Product> filtered = new ArrayList<>();

        for (Product product : products) {
            boolean matches = true;
            if (name != null && !product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            if (categoryId != null && product.getCategoryId() != categoryId) {
                matches = false;
            }
            if (unitPrice != null && product.getUnitPrice() != unitPrice) {
                matches = false;
            }
            if (matches) {
                filtered.add(product);
            }
        }
        return filtered;
    }

    @GetMapping("/products/{id}")
    public Product getProductById ( @PathVariable int id){
//        for (Product product : getProductList()) {
//            if (product.getProductId() == id) {
//                return product;
            return productDao.getById(id);
//            }
//        }
       // return null;
    }
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product){
        return productDao.insert(product);
    }

}

