package com.ymkj.smi.web.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.dom4j.tree.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
* Customer
* <p/>
* Author: 
* Date: 2017-07-24 18:31:45
* Mail: 
*/
@Getter
@Setter
public class Customer{
	
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

}