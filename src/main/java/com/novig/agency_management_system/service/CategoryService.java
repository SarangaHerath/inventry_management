package com.novig.agency_management_system.service;

import com.novig.agency_management_system.dto.requestDto.RequestCategoryDto;
import com.novig.agency_management_system.entity.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(RequestCategoryDto requestCategoryDto);

    List<Category> getAllCategory();

    Category updateCategory(RequestCategoryDto requestCategoryDto);

    String deleteCategory(Long id);
}
