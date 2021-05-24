package com.example.fulldev.service;

import com.example.fulldev.model.Banner;
import com.example.fulldev.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiseImpl implements BannerServise {
    @Autowired
    private BannerRepository bannerRepository;
    @Override
    public Banner getByName(String name) {
        return bannerRepository.findOneByName(name);
    }

}
