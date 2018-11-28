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
* AdminUser
* <p/>
* Author: 
* Date: 2017-07-24 18:31:38
* Mail: 
*/
@Table(name = "admin_user")
@Getter
@Setter
public class AdminUser extends AbstractEntity<Long> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8407389205662083570L;

	@Id
    private Long id;

    /**
    * 工号
    */
    private String jno;

    /**
    * 用户名
    */
    private String name;

    /**
    * 手机号
    */
    private String phone;

    /**
    * 密码
    */
    private String password;

    /**
    * 员工类型
    */
    private String eType;

    /**
    * 状态
    */
    private String status;

    /**
    * 备注
    */
    private String memo;

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
     * 父节点
     */
    @Transient
    private String parentNode; 
    
    /**
     * 角色名
     */
    @Transient
    private String roleName;
    
    /**
     * 角色Id
     */
    @Transient
    private Long rolId;

}