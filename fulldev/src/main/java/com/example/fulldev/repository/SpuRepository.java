package com.example.fulldev.repository;

import com.example.fulldev.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpuRepository extends JpaRepository<Spu, Long> {
    Spu findOneById(Long id);

    Page<Spu> findAllByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);

    Page<Spu> findAllByRootCategoryId(Long cid, Pageable pageable);
}
