package com.ymkj.smi.manager.common.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import com.ymkj.springside.modules.orm.AbstractEntity;

/**
* ValidateCode
* <p/>
* Author: changj
* Date: 2017-08-28 09:39:41
* Mail: 
*/
@Table(name = "validate_code")
@Getter
@Setter
public class ValidateCode extends AbstractEntity<Long> {
    @Id
    private Long id;


    /**
    * 手机号
    */
    private String mobile;

    /**
    * 验证码
    */
    private String validateCode;

    /**
    * 类型
    */
    private String type;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
    * 尝试次数
    */
    private Integer tryTime;

}