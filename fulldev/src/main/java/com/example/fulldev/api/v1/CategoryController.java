package com.example.fulldev.api.v1;

import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.model.GridCategory;
import com.example.fulldev.service.CategoryService;
import com.example.fulldev.service.GridCategoryService;
import com.example.fulldev.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GridCategoryService gridCategoryService;

    @RequestMapping("/all")
    public CategoriesAllVO getall() {
        Map categoryServiceAll = categoryService.getAll();
        return new CategoriesAllVO(categoryServiceAll);
    }

    @RequestMapping("/grid/all")
    public List<GridCategory> getGridCategoryall() {
        List<GridCategory> gridCategories = gridCategoryService.getGridCategoryList();
        if (gridCategories.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return gridCategories;
    }
}
