package com.demo.test.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;


/**
 * @Author : Hyper
 * @Time : 2018/10/11 14:57
 */
@Entity
@Table(name = "t_userinfo")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 0, max = 32)
    private String name;

    private Integer age;
    @Size(min = 0, max = 255)
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

