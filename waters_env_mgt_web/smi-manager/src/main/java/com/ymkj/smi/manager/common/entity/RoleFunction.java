package com.ymkj.smi.manager.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ymkj.springside.modules.orm.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

/**
* RoleFunction
* <p/>
* Author: 
* Date: 2017-07-24 18:32:08
* Mail: 
*/
@Table(name = "role_function")
@Getter
@Setter
public class RoleFunction extends AbstractEntity<Long> {
    @Id
    private Long id;

    /**
    * 角色表_ID
    */
    private Long rolId;

    /**
    * 菜单表_ID
    */
    private Long funcId;

}