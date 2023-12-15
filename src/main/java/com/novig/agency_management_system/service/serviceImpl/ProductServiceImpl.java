package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.ProductDto;
import com.novig.agency_management_system.dto.requestDto.RequestProductDto;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.repository.ProductRepo;
import com.novig.agency_management_system.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product addProduct(RequestProductDto requestProductDto) {
        try {
            Product product = new Product(
                    requestProductDto.getProductId(),
                    requestProductDto.getProductName(),
                    requestProductDto.getWeight(),
                    requestProductDto.getDate(),
                    requestProductDto.getUnitPrice(),
                    requestProductDto.getQuantity()
            );
            productRepo.save(product);
            return product;
        } catch (Exception e) {

            throw new RuntimeException("Error saving product: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = productRepo.findAll();
        return productList;
    }

    @Override
    public String deleteProduct(Long id) {
        productRepo.deleteById(id);
        return "Deleted !!";
    }

    @Override
    public Product updateProduct(RequestProductDto requestProductDto) {
        try {
            Optional<Product> productOptional = productRepo.findById(requestProductDto.getProductId());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setProductName(requestProductDto.getProductName());
                product.setWeight(requestProductDto.getWeight());
                product.setDate(requestProductDto.getDate());
                product.setUnitPrice(requestProductDto.getUnitPrice());
                product.setQuantity(requestProductDto.getQuantity());


                return productRepo.save(product);
            } else {
                throw new EntityNotFoundException("Product not found with id: " + requestProductDto.getProductId());
            }
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Error updating product", e);
        }
    }




    public Product findOrCreateProduct(Long productId) {
        // Attempt to find the product by ID
        Optional<Product> optionalProduct = productRepo.findById(productId);

        // If the product is found, return it
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            // If the product is not found, create a new one
            Product newProduct = new Product();
            // Set any necessary fields for the new product
            // ...

            // Save and return the new product
            return productRepo.save(newProduct);
        }
    }

    public Product findProductById(Long productId) {
        // Assuming ProductRepository has a method to find by ID
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    public ProductDto convertToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setQuantity(product.getQuantity());
        productDto.setUnitPrice(product.getUnitPrice());
        // set other fields if necessary
        return productDto;
    }
}
