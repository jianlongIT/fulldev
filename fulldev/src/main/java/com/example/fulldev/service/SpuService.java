package com.example.fulldev.service;

import com.example.fulldev.model.Spu;
import org.springframework.data.domain.Page;

public interface SpuService {
    public Spu getSpuById(Long id);

    public Page<Spu> getlatestPagingspu(Integer pageNum, Integer size);

    public Page<Spu> getByCategory(Long cid,Boolean isRoot,Integer pageNum, Integer size);
}
