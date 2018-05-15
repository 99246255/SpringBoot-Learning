package com.example.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


/**
 * 用户
 */
@Entity(name = "user")
@Table(name = "sys_user")
@SQLDelete(sql = "Update sys_user set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @Column(columnDefinition="varchar(255) NOT NULL COMMENT '姓名'")
    private String name;

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


}