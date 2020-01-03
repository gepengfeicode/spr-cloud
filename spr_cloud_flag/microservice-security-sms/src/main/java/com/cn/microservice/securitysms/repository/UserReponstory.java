package com.cn.microservice.securitysms.repository;

import com.cn.microservice.securitysms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReponstory extends JpaRepository<User, Integer> {
    User findByName(String userName);
}
