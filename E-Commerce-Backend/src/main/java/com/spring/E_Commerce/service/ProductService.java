package com.spring.E_Commerce.service;

import com.spring.E_Commerce.dao.ProductDAO;
import com.spring.E_Commerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public List<Product> getAllProducts() {
       return productDAO.findAll();
    }

    public Product getProductById(int theId) {
        return productDAO.findById(theId).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return productDAO.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {

        Product existingProduct = productDAO.findById(id).get();

        if (existingProduct != null) {
            // Update the existing product with the provided product's details
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setReleaseDate(product.getReleaseDate());
            existingProduct.setProductAvailable(product.isProductAvailable());
            existingProduct.setStockQuantity(product.getStockQuantity());

            // Update image data if an image file is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                existingProduct.setImageData(imageFile.getBytes());
                existingProduct.setImageType(imageFile.getContentType());
                existingProduct.setImageName(imageFile.getOriginalFilename());
            }
            return productDAO.save(existingProduct);
        } else {
            return null;
        }
    }

    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }

    public List<Product> seearchProducts(String keyword) {
       return productDAO.searchProduct(keyword);
    }
}










