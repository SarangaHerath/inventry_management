package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestStockOutDto;
import com.novig.agency_management_system.dto.responseDto.CategoryWiseProductDto;
import com.novig.agency_management_system.entity.Product;
import com.novig.agency_management_system.entity.StockOut;
import com.novig.agency_management_system.entity.Vehicle;
import com.novig.agency_management_system.repository.ProductRepo;
import com.novig.agency_management_system.repository.StockOutRepo;
import com.novig.agency_management_system.repository.VehicleRepo;
import com.novig.agency_management_system.service.ProductService;
import com.novig.agency_management_system.service.StockOutService;
import com.novig.agency_management_system.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockOutServiceImpl implements StockOutService {

    @Autowired
    private StockOutRepo stockOutRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<StockOut> getAllStockOut() {
        List<StockOut> stockOutList = stockOutRepo.findAll();
        return stockOutList;
    }

    @Override
    public String deleteStockOut(Long id) {
        stockOutRepo.deleteById(id);
        return "Deleted !!";
    }

    @Override
    public StockOut updateStockOut(RequestStockOutDto requestStockOutDto) {
        Optional<StockOut> existingStockOutOptional = stockOutRepo.findById(requestStockOutDto.getStockOutId());

        if (existingStockOutOptional.isPresent()) {
            StockOut existingStockOut = existingStockOutOptional.get();

            // Get the existing quantity before the update
            int existingQuantity = existingStockOut.getQuantity();

            // Update the StockOut details
            existingStockOut.setQuantity(requestStockOutDto.getQuantity());
            existingStockOut.setDateOut(requestStockOutDto.getDateOut());

            Optional<Product> productOptional = productRepo.findById(requestStockOutDto.getProductId());
            Optional<Vehicle> vehicleOptional = vehicleRepo.findById(requestStockOutDto.getVehicleId());

            if (productOptional.isPresent() && vehicleOptional.isPresent()) {
                Product product = productOptional.get();
                Vehicle vehicle = vehicleOptional.get();

                existingStockOut.setProduct(product);
                existingStockOut.setVehicle(vehicle);

                // Calculate the difference in quantity
                int quantityDifference = requestStockOutDto.getQuantity() - existingQuantity;

                // Update the Product quantity
                int updatedProductQuantity = product.getQuantity() - quantityDifference;
                product.setQuantity(updatedProductQuantity);
                productRepo.save(product);

                return stockOutRepo.save(existingStockOut);
            } else {
                throw new IllegalArgumentException("Invalid Product or Vehicle ID");
            }
        } else {
            throw new IllegalArgumentException("StockOut entry not found");
        }
    }

    @Override
    public StockOut addStockOut(RequestStockOutDto requestStockOutDto) {
        // Retrieve the existing Product entity by ID
        Optional<Product> productOptional = productRepo.findById(requestStockOutDto.getProductId());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Check if the requested quantity is less than or equal to the available quantity
            if (requestStockOutDto.getQuantity() <= product.getQuantity()) {
                // Create a new StockOut entry
                StockOut newStockOut = new StockOut();
                newStockOut.setQuantity(requestStockOutDto.getQuantity());
                newStockOut.setDateOut(requestStockOutDto.getDateOut());

                // Retrieve the associated Vehicle entity
                Optional<Vehicle> vehicleOptional = vehicleRepo.findById(requestStockOutDto.getVehicleId());

                if (vehicleOptional.isPresent()) {
                    Vehicle vehicle = vehicleOptional.get();
                    newStockOut.setVehicle(vehicle);

                    // Set the associated Product entity
                    newStockOut.setProduct(product);

                    // Update the product quantity
                    int updatedProductQuantity = product.getQuantity() - requestStockOutDto.getQuantity();
                    product.setQuantity(updatedProductQuantity);
                    productRepo.save(product);

                    // Save the new StockOut entry
                    return stockOutRepo.save(newStockOut);
                } else {
                    // Handle the case where the Vehicle does not exist
                    throw new IllegalArgumentException("Invalid Vehicle ID");
                }
            } else {
                // Handle the case where the requested quantity is greater than the available quantity
                throw new IllegalArgumentException("Requested quantity exceeds available quantity");
            }
        } else {
            // Handle the case where the Product does not exist
            throw new IllegalArgumentException("Invalid Product ID");
        }
    }
    @Override
    public List<StockOut> getOutOfStockProductDetails(Long productId) {
        // Implement logic to retrieve out-of-stock product details based on productId
        // For example, you can query the database for StockOut entries where product ID matches.

        List<StockOut> outOfStockDetails = stockOutRepo.findByProduct_ProductId(productId);
        return outOfStockDetails;
    }

    @Override
    public StockOut getByIdStockOut(Long id) {
        try {
            return stockOutRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("StockOut not found with id: " + id));
        } catch (Exception e) {
            // Log the exception or handle it according to your application's needs
            throw new RuntimeException("Error fetching StockOut by id: " + id, e);
        }
    }

    @Override
    @Transactional
    public List<CategoryWiseProductDto> getProductsByCategory(Long categoryId) {
        List<StockOut> stockOutList = stockOutRepo.findByProduct_Category_CategoryId(categoryId);
        List<CategoryWiseProductDto> result = new ArrayList<>();

        for (StockOut stockOut : stockOutList) {
            CategoryWiseProductDto dto = new CategoryWiseProductDto();
            dto.setProductId(stockOut.getProduct().getProductId());
            dto.setProductName(stockOut.getProduct().getProductName());
            dto.setQuantity(stockOut.getQuantity());
            dto.setUnitPrice(stockOut.getProduct().getUnitPrice());
            dto.setWeight(stockOut.getProduct().getWeight());
            result.add(dto);
        }
        return result;
    }

}
