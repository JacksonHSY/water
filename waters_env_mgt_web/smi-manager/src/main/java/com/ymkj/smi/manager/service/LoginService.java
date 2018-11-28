package com.ymkj.smi.manager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.mapper.AdminUserMapper;
import com.ymkj.smi.manager.mapper.LoginMapper;
import com.ymkj.springside.modules.utils.StrUtils;

@Service
public class LoginService {
	@Autowired
	private LoginMapper loginMapper;
	 
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	/**
	 * @param AdmianUser
	 * @author YM10203
	 * 查询登入的信息校验
	 * */
	public List<AdminUser> selectParams(AdminUser user){
//		List<AdminUser> retList = new ArrayList<AdminUser>();
//		Map<String, Object> map = new HashMap<String,Object>();
//		if(StrUtils.isNotBlank(user.getName())){
//			map.put("name", user.getName());
//		}
		Example example = new Example(AdminUser.class);
		Criteria criteria =  example.createCriteria();
		criteria.andEqualTo(user);
		return adminUserMapper.selectByExample(example);
		
//		retList = loginMapper.selectByName(map);
		
		/*for( AdminUser users : list){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", users.getId());
			map.put("password", users.getPassword());
			retList.add(map);
		}*/
//		 return retList;
	}

}
