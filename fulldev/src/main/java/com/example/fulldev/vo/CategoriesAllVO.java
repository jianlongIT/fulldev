package com.example.fulldev.vo;

import com.example.fulldev.model.Category;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriesAllVO {
    private List<CategoryPureVO> roots;

    private List<CategoryPureVO> subs;

    public CategoriesAllVO(Map<Integer, List<Category>> map) {
        this.roots = map.get(1).stream().map(CategoryPureVO::new).collect(Collectors.toList());
        this.subs = map.get(2).stream().map(CategoryPureVO::new).collect(Collectors.toList());
    }

    public List<CategoryPureVO> getRoots() {
        return roots;
    }

    public void setRoots(List<CategoryPureVO> roots) {
        this.roots = roots;
    }

    public List<CategoryPureVO> getSubs() {
        return subs;
    }

    public void setSubs(List<CategoryPureVO> subs) {
        this.subs = subs;
    }
}
