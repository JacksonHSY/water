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
* CustomerShip
* <p/>
* Author: 
* Date: 2017-07-24 18:31:49
* Mail: 
*/
@Table(name = "customer_ship")
@Getter
@Setter
public class CustomerShip extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 客户ID
    */
    private Long cusId;

    /**
    * MMSI码
    */
    private String mmsiCode;

    /**
    * 单位地址
    */
    private String address;

    /**
    * 船舶性质
    */
    private String sNature;

    /**
    * 船名/单位
    */
    private String sName;

    /**
    * 状态
    */
    private String status;

    /**
    * 创建人
    */
    private String creater;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
    * 更新时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Transient
    private String userName;
    
    @Transient
    private String cusName;
    
    @Transient
    private String phone;
    
    @Transient
    private String password;
    
    @Transient
    private Long customerShipId;
    
    @Transient
    private String customerShipName;
}