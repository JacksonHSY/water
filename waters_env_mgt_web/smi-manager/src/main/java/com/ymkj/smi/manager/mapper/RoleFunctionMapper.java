package com.ymkj.smi.manager.mapper;

import java.util.List;

import com.ymkj.smi.manager.common.entity.RoleFunction;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* RoleFunctionMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:32:08
* Mail: 
*/
public interface RoleFunctionMapper extends JdMapper<RoleFunction, Long> {

	public List<RoleFunction> queryRoleFunctionByRoleId(Long roleId);

}