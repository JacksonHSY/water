package com.ymkj.smi.manager.common.entity;

import com.ymkj.springside.modules.orm.AbstractEntity;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
* SysLog
* <p/>
* Author: longjw
* Date: 2016-11-08 19:13:46
* Mail: longjw@yuminsoft.com
*/
@Table(name = "SYS_LOG")
@Getter
@Setter
public class SysLog extends AbstractEntity<Long> {
    
	private static final long serialVersionUID = -1918850396317862174L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_LOG.nextval from dual")
    private Long id;

    /**
    * 操作人工号
    */
    private String staffNo;

    /**
    * 操作人姓名
    */
    private String staffName;

    /**
    * 操作类型
    */
    private String type;

    /**
    * 内容
    */
    private String content;

    /**
    * 时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;

    /**
    * 备注
    */
    private String memo;
    
    /**
     * 操作Ip
     */
    private String lgIp;
     
     /**
      * 日志发生的地方，可记录模块名称
      */
    private String lgSource;
    /**
     * 登录名userName
     */
    private String userName;
    /**
     * 操作结果
     */
    private String actionResult;
    
    /**
     * 状态/是否显示（0-不显示 1-显示）
     */
    private String status;
    
    @Transient
    private Date actionTimeStart;
    @Transient
    private Date actionTimeEnd;
     

}