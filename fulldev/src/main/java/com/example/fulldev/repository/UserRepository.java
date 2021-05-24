package com.example.fulldev.repository;

import com.example.fulldev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByOpenid(String openid);

    User findFirstById(Long id);

    User findByUnifyUid(Long uuid);
}
