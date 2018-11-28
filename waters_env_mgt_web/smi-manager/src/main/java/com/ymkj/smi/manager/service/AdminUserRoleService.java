package com.ymkj.smi.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ymkj.smi.manager.common.entity.AdminUserRole;
import com.ymkj.smi.manager.mapper.AdminUserRoleMapper;

/**
* AdminUserRoleService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:41
* Mail: 
*/
@Service
public class AdminUserRoleService{

	@Autowired
	private AdminUserRoleMapper adminUserRoleMapper;
	
	public boolean checkAdminUserRole(Long adminUserId){
		if(null!=adminUserId){
			AdminUserRole adminUserRole = getAdminUserRole(adminUserId);
			if(null!=adminUserRole){
				return true;
			}
		}
		
		return false;
	}
	
	public AdminUserRole getAdminUserRole(Long adminUserId){
		AdminUserRole adminUserRole = null;
		Example example = new Example(AdminUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("admId", adminUserId);
		List<AdminUserRole> list = adminUserRoleMapper.selectByExample(example);
		if(list.size()>0){
			adminUserRole = list.get(0);
			return adminUserRole;
		} 
		return null;
	}

	public void insert(AdminUserRole adminUserRole) {
		
		adminUserRoleMapper.insert(adminUserRole);
	}

	public void updateAdminUserRole(AdminUserRole adminUserRole) {
		
		adminUserRoleMapper.updateByPrimaryKeySelective(adminUserRole);
	}

	public void deleAdminUserRole(Long adminUserRoleId) {
		
		adminUserRoleMapper.deleteByPrimaryKey(adminUserRoleId);
	}
	
}