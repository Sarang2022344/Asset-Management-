package com.asset.management.controller;

import com.asset.management.service.CategoryServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class AssetRegisterController {
private final CategoryServiceImpl categoryService;

    public AssetRegisterController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }






}
