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
* Dictionary
* <p/>
* Author: 
* Date: 2017-07-24 18:31:53
* Mail: 
*/
@Table(name = "dictionary")
@Getter
@Setter
public class Dictionary extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 字典类型
    */
    private String dataType;

    /**
    * 字典编码
    */
    private String dataCode;

    /**
    * 字典名称
    */
    private String dataName;

    /**
    * 字典值
    */
    private String dataValue;

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
    
    private String memo;

}