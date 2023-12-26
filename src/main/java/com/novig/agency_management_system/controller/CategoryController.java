package com.novig.agency_management_system.controller;

import com.novig.agency_management_system.dto.requestDto.RequestCategoryDto;
import com.novig.agency_management_system.entity.Category;
import com.novig.agency_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Category> addCategory(@RequestBody RequestCategoryDto requestCategoryDto) {
        Category category = categoryService.addCategory(requestCategoryDto);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategory(){
         List<Category> list = categoryService.getAllCategory();
         return  ResponseEntity.ok(list);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody RequestCategoryDto requestCategoryDto){
        Category category = categoryService.updateCategory(requestCategoryDto);
        return ResponseEntity.ok(category);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        String status = categoryService.deleteCategory(id);
        return ResponseEntity.ok(status);
    }
}
