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
* Announcement
* <p/>
* Author: 
* Date: 2017-07-24 18:31:43
* Mail: 
*/
@Table(name = "announcement")
@Getter
@Setter
public class Announcement extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 标题
    */
    private String title;

    /**
    * 发布频道
    */
    private String releaseChannel;

    /**
    * 发布人
    */
    private String issue;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
    * 更新时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
    * 状态
    */
    private String status;

    /**
    * 内容
    */
    private String content;
    /**
     * 
     */
    @Transient
    private String createTimeStr;
}