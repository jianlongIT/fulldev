package com.example.fulldev.service;

import com.example.fulldev.repository.ThemeRepository;
import com.example.fulldev.model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> findByNames(List<String> names) {
        return themeRepository.findByNames(names);
    }

    public Optional<Theme> findByName(String name) {
        return themeRepository.findByName(name);
    }
}
