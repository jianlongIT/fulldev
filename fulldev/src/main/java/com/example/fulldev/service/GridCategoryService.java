package com.example.fulldev.service;

import com.example.fulldev.model.GridCategory;
import com.example.fulldev.repository.GridCategoryReposorory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCategoryService {
    @Autowired
    private GridCategoryReposorory gridCategoryRepository;

    public List<GridCategory> getGridCategoryList() {
        return gridCategoryRepository.findAll();
    }

}
