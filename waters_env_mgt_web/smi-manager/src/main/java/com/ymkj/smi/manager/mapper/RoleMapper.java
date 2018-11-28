package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* RoleMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:32:05
* Mail: 
*/
public interface RoleMapper extends JdMapper<Role, Long> {

	public List<Function> queryFunctionByUserIdAndFunctionCode(Map<String, Object> map);

	public List<Function> queryFunctionByRoleId(String roleId);

	public List<Role> getRoleByJno(String jno);

}