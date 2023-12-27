package com.novig.agency_management_system.service.serviceImpl;

import com.novig.agency_management_system.dto.requestDto.RequestCategoryDto;
import com.novig.agency_management_system.entity.Category;
import com.novig.agency_management_system.exception.CategoryNotFoundException;
import com.novig.agency_management_system.exception.DuplicateCategoryException;
import com.novig.agency_management_system.repository.CategoryRepo;
import com.novig.agency_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category addCategory(RequestCategoryDto requestCategoryDto) {
        try {
            Category category = new Category();
            category.setCategoryName(requestCategoryDto.getCategoryName());
            return categoryRepo.save(category);
        } catch (DataIntegrityViolationException e) {
            // Handle data integrity violation (e.g., duplicate entry)
            // You might want to log the exception or perform additional handling
            throw new DuplicateCategoryException("Category with the same name already exists");
        } catch (Exception e) {
            // Handle other exceptions
            // You might want to log the exception or perform additional handling
            throw new RuntimeException("An error occurred while adding the category", e);
        }
    }

    @Override
    public List<Category> getAllCategory() {
        try {
            List<Category> list = categoryRepo.findAll();
            return list;
        } catch (Exception e) {
            // Handle the exception
            throw new RuntimeException("An error occurred while fetching categories", e);
        }
    }

    @Override
    public Category updateCategory(RequestCategoryDto requestCategoryDto) {
        try {
            Optional<Category> optionalCategory = categoryRepo.findById(requestCategoryDto.getCategoryId());

            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setCategoryName(requestCategoryDto.getCategoryName());
                return categoryRepo.save(category);
            } else {
                throw new CategoryNotFoundException("Category not found with ID: " + requestCategoryDto.getCategoryId());
            }
        } catch (Exception e) {
            // Handle the exception
            throw new RuntimeException("An error occurred while updating the category", e);
        }
    }

    @Override
    public String deleteCategory(Long id) {
        try {
            categoryRepo.deleteById(id);
            return "Deleted successfully!";
        } catch (EmptyResultDataAccessException e) {
            // Handle the case where the category with the given ID is not found
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("An error occurred while deleting the category", e);
        }
    }

}