package com.example.fulldev.service;

import com.example.fulldev.model.Sku;
import com.example.fulldev.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {

    @Autowired
    private SkuRepository skuRepository;

    public List<Sku> getSkuListByOds(List<Long> ids) {
        List<Sku> skuList = skuRepository.findAllByIdIn(ids);
        return skuList;
    }
}
