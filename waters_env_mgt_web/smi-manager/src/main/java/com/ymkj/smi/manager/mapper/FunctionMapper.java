package com.ymkj.smi.manager.mapper;

import java.util.List;

import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* FunctionMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:57
* Mail: 
*/
public interface FunctionMapper extends JdMapper<Function, Long> {

	public List<Function> getFuncList(Function fn);

	public List<Function> getMenuByRoleId(String roleId);

}