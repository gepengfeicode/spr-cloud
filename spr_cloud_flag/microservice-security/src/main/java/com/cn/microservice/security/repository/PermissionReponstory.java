package com.cn.microservice.security.repository;

import com.cn.microservice.security.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionReponstory extends JpaRepository<Permission,Integer> {
    @Query(nativeQuery = true,value = "select permissionName from sys_permission sp inner join sys_role_permission srp on sp.id = srp.pid where srp.rid = ?1")
    List<String> findPermissionNameByRoleId(Integer roleId);
}
