package com.cn.microservice.securitysms.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
@Table(name = "sys_user")
public class User implements java.io.Serializable {
    @Id
    @Column(name = "id",unique = true)
    private Integer id;
    @Column(name = "name",length = 40)
    private String name;
    @Column(name = "password",length = 40)
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
