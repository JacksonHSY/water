package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* AdminUserMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:38
* Mail: 
*/
public interface AdminUserMapper extends JdMapper<AdminUser, Long> {

	public List<AdminUser> getAdminUserList(Map<String, Object> map);

	public List<AdminUser> getAdminUser(AdminUser sf);

}