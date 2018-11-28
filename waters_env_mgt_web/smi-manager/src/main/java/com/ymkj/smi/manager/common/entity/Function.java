package com.ymkj.smi.manager.common.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
* Function
* <p/>
* Author: 
* Date: 2017-07-24 18:31:57
* Mail: 
*/
@Table(name = "function")
@Getter
@Setter
public class Function extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_function.nextval from dual")
    private Long id;

    /**
    * 菜单名
    */
    private String fName;

    /**
    * 菜单类型
    */
    private String fType;

    /**
    * 菜单编码
    */
    private String fCode;

    /**
    * 父节点
    */
    private String parentNode;

    /**
    * 页面URL
    */
    private String url;

    /**
    * 排序
    */
    private String sort;

    /**
    * 描述
    */
    private String describer;

    /**
    * 状态
    */
    private String status;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createpTime;

    /**
    * 更新时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Transient
    private String checked;
}