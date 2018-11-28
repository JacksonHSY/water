package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;


public interface LoginMapper  extends JdMapper<AdminUser, Long>{
	public List<AdminUser> selectByName(Map<String, Object> map);
}
