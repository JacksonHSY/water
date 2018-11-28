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
* WorkShip
* <p/>
* Author: 
* Date: 2017-07-24 18:32:10
* Mail: 
*/
@Table(name = "work_ship")
@Getter
@Setter
public class WorkShip extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 船名
    */
    private String name;

    /**
    * 船舶性质
    */
    private String shipNature;

    /**
    * 船长
    */
    private String captain;

    /**
    * 备注
    */
    private String memo;

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
    private Long workShipId;
    
    @Transient
    private String workShipName;
    
    @Transient
    private String sName;//客户船名

}