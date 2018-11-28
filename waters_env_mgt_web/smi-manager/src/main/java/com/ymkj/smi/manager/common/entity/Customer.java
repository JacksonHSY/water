package com.ymkj.smi.manager.common.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
* Customer
* <p/>
* Author: 
* Date: 2017-07-24 18:31:45
* Mail: 
*/
@Table(name = "customer")
@Getter
@Setter
public class Customer extends AbstractEntity<Long> {
	
    @Id
    private Long id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 姓名
    */
    private String cusName;

    /**
    * 联系电话
    */
    private String phone;

    /**
    * 密码
    */
    private String password;

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
    
    /**
     * MMSI码
     */
    @Transient
     private String mmsiCode;

     /**
     * 单位地址
     */
    @Transient
     private String address;

     /**
     * 船舶性质
     */
    @Transient
     private String sNature;

     /**
     * 船名/单位
     */
    @Transient
     private String sName;
    /**
     * 船id
     */
    @Transient
    private Long shipId;

}