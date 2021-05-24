package com.example.fulldev.service;

import com.example.fulldev.model.Spu;
import com.example.fulldev.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu getSpuById(Long id) {
        return spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getlatestPagingspu(Integer pageNum, Integer size) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        return this.spuRepository.findAll(pageable);
    }

    @Override
    public Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        Page<Spu> spuPage = null;
        if (isRoot) {
            spuPage = this.spuRepository.findAllByRootCategoryId(cid, pageable);
        } else {
            spuPage = this.spuRepository.findAllByCategoryIdOrderByCreateTimeDesc(cid, pageable);
        }
        return spuPage;
    }
}
