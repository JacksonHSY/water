package com.ymkj.smi.manager.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ymkj.springside.modules.orm.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

/**
* AdminUserRole
* <p/>
* Author: 
* Date: 2017-07-24 18:31:41
* Mail: 
*/
@Table(name = "admin_user_role")
@Getter
@Setter
public class AdminUserRole extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 角色ID
    */
    private Long rolId;

    /**
    * 用户ID
    */
    private Long admId;

}