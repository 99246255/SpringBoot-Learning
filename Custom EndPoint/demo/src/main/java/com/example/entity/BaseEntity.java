package com.example.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 启动服务类加上@EnableJpaAuditing
 * 自动生成id、创建时间、创建人、修改时间、修改者
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable{

    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 删除标记
     */
    @Column(columnDefinition="bit(1) NOT NULL COMMENT '删除标记：0未删除，1删除'")
    private boolean isDeleted = false;
    /**
     * 创建人id
     */
    @CreatedBy
    @Column(columnDefinition="bigint NOT NULL COMMENT '创建人id'")
    private Long createBy;

    /**
     * 创建时间
     */
    @CreatedDate
    @DateTimeFormat(pattern = DATEFORMAT)
    @Column(columnDefinition="datetime NOT NULL COMMENT '创建时间'")
    private Date createTime;

    /**
     * 更新人
     */
    @LastModifiedBy
    @Column(columnDefinition="bigint(20) NOT NULL COMMENT '更新人id'")
    private Long updateBy;
    /**
     * 更新时间
     */
    @LastModifiedDate
    @DateTimeFormat(pattern = DATEFORMAT)
    @Column(columnDefinition="datetime NOT NULL COMMENT '更新时间'")
    private Date updateTime;





    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
