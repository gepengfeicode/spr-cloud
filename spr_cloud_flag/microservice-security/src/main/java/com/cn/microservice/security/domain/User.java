package com.cn.microservice.security.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "sys_user")
public class User implements java.io.Serializable {
    @Id
    @Column(name = "Id",unique = true)
    private Integer id;
    @Column(name = "name",length = 40)
    private String name;
    @Column(name = "password",length = 40)
    private String password;

}
