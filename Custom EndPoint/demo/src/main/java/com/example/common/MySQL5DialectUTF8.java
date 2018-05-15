package com.example.common;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 设置mysql数据库方言,否则自动建表可能会有问题
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }

}
