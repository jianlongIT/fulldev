package com.example.fulldev.service;

import com.example.fulldev.model.Category;
import com.example.fulldev.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Map getAll() {
        List<Category> roots = categoryRepository.findAllByIsRootOrderByIndex(true);
        List<Category> subs = categoryRepository.findAllByIsRootOrderByIndex(false);
        Map categories = new HashMap();
        categories.put(1, roots);
        categories.put(2, subs);
        return categories;
    }
}
