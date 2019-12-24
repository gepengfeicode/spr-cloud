package com.cn.microservice.security.repository;

import com.cn.microservice.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleReponstory extends JpaRepository<Role, Integer> {
    /*根据用户名获取角色信息*/
    @Query(nativeQuery = true, value = "select sr.* from sys_role sr inner join sys_user_role sur on sr.id = sur.rid where sur.uid = ?1")
    List<Role> findRoleByUserName(Integer userId);
}
