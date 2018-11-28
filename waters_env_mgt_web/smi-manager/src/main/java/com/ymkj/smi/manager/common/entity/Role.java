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
* Role
* <p/>
* Author: 
* Date: 2017-07-24 18:32:05
* Mail: 
*/
@Table(name = "role")
@Getter
@Setter
public class Role extends AbstractEntity<Long> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -912589427981243208L;

	@Id
    private Long id;

    /**
    * 角色编码
    */
    private String roleCode;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 状态
    */
    private String status;

    /**
    * 备注
    */
    private String meno;

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