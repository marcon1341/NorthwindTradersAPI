package com.Pluralsight1.NorthwindTradersAPI.dao;

import com.Pluralsight1.NorthwindTradersAPI.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {
    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryID"));
                product.setUnitPrice(rs.getDouble("UnitPrice"));
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;

    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products WHERE ProductID = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryID"));
                product.setUnitPrice(rs.getDouble("UnitPrice"));
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";
        try(
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, product.getProductName());
                stmt.setInt(2,product.getCategoryId());
                stmt.setDouble(3,product.getUnitPrice());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0){
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()){
                        int newId = generatedKeys.getInt(1);
                        product.setProductId(newId);
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }
}
