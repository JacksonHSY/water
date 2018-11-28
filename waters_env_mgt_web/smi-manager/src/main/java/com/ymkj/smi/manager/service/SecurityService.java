package com.ymkj.smi.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.mapper.AdminUserMapper;
import com.ymkj.smi.manager.mapper.FunctionMapper;
import com.ymkj.smi.manager.mapper.RoleMapper;



@Service
public class SecurityService {
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private FunctionMapper functionMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	
	//根据工号取得员工
	public AdminUser getStaffByStaffNo(String jno){
		AdminUser user=new AdminUser();
		AdminUser sf=new AdminUser();
		sf.setJno(jno);
		List<AdminUser> staffList=adminUserMapper.getAdminUser(sf);
		if(staffList!=null){
			if(!staffList.isEmpty()){
				user=staffList.get(0);
			}
		}
		return user;
	}
	//取得所有菜单
	public List<Function> getMenuList(){
		Function fn=new Function();
		fn.setFType("1");
		List<Function> menuList=functionMapper.getFuncList(fn);
		return menuList;
	}
	//根据工号取得角色列表
	public List<Role> getRoleByJno(String jno){
		return roleMapper.getRoleByJno(jno);
	}
	//根据角色取得菜单列表
	public List<Function> getMenuByRoleId(String roleId){
		return functionMapper.getMenuByRoleId(roleId);
	}
	//根据function主键取得菜单
	public Function getMenuByFunctionId(String functionId){
		Function f=new Function();
		Function fn=new Function();
		fn.setId(Long.valueOf(functionId));
		fn.setFType("1");
		List<Function> menuList=functionMapper.getFuncList(fn);
		if(menuList!=null){
			if(!menuList.isEmpty()){
				f=menuList.get(0);
			}
		}
		return f;
	}
}
