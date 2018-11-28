package com.ymkj.smi.manager.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.common.entity.MenuTreeModelRole;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.common.entity.RoleFunction;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.mapper.RoleFunctionMapper;
import com.ymkj.smi.manager.mapper.RoleMapper;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;



/**
* RoleService
* <p/>
* Author: liangj@yuminsoft.com
* Date: 2017-07-24 18:32:05
* Mail: 
*/
@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	
	@Autowired
	private AdminUserService adminUserService;
	
	/**
	 * 查询用户角色对应菜单
	 * @param user
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response getFunctionByCondtion(AdminUser user) {
		return Response.success(queryFunctionByRoleIdsAndParentCode(user.getId(), user.getParentNode()));
	}

	/**
	 * 查询角色对应菜单
	 * @param userId
	 * @param parentNode
	 * @return
	 * List<Function>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<Function> queryFunctionByRoleIdsAndParentCode(Long userId, String parentNode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fType", "1");//菜单标识
		map.put("userId", userId);
		map.put("parentNode", parentNode);
		return roleMapper.queryFunctionByUserIdAndFunctionCode(map);
	}
	
	/**
	 * 角色查询
	 * @param pageInfo
	 * @return
	 * PageInfo<Role>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<Role> getPage(PageInfo<Role> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Role role = (Role) pageInfo.getQueryParam();
		Page<Role> page = (Page)getPageList(role);
        return PageUtils.convertPage(page);
	}
	
	/**
	 * 角色查询
	 * @param role
	 * @return
	 * List<Role>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<Role> getPageList(Role role) {
		Example example = new Example(Role.class);
		Criteria  cr= example.createCriteria(); 
        if(StringUtils.isNotBlank(role.getRoleName())){
        	cr.andEqualTo("roleName",role.getRoleName());        	
        }
        if(StringUtils.isNotBlank(role.getRoleCode())){
        	cr.andEqualTo("roleCode",role.getRoleCode());        	
        }
        if(StringUtils.isNotBlank(role.getStatus())){
        	cr.andEqualTo("status",role.getStatus());        	
        }
//        example.orderBy("createTime").desc();
        return roleMapper.selectByExample(example);
	}
	
	/**
	 * 获取权限菜单
	 * @param rolId
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response getRoleMenu(String rolId ) {
//		String rolId = (String) request.getBody();
		List<Function> menuList = queryFunctionByRoleId(rolId);
		List<MenuTreeModelRole> treeList = this.setTree(Constants.SYS_PARENT_ID,menuList);
		return Response.success(treeList);
	}
	
	/**
	 * 递归方法体
	 * @param parentId
	 * @param menuList
	 * @return
	 * List<MenuTreeModelRole>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public  List<MenuTreeModelRole> setTree(String parentId,List<Function> menuList) {
		List<MenuTreeModelRole> treeList = new ArrayList<MenuTreeModelRole>();
		for (int i = 0; i < menuList.size(); i++) {
			Function menu = menuList.get(i);
			//整个过程（无论是递归还是for循环）使用的都是同一个数据集(节点集合)来为树设置节点(每次使用时会根据条件筛选出来若干节点)
			//设置节点的顺序：从根节点开始从上至下遍历，(根节点的parent_id=-1，作为起始入口)
			if(parentId.equals(menu.getParentNode())){//设置开始： 第一次得到根节点，设置信息
				//*****************
				MenuTreeModelRole tree=new MenuTreeModelRole();
				Map<String,Object> attributes=new HashMap<String, Object>();
				attributes.put("type", menu.getFType());
				attributes.put("name", menu.getFName());
				tree.setAttributes(attributes);
				tree.setId(String.valueOf(menu.getId()));
				tree.setText(menu.getFName());
				tree.setUrl(menu.getUrl());
				if("true".equals(menu.getChecked())){
					tree.setChecked(true);					
				}else{
					tree.setChecked(false);
				}
				tree.setExpanded(true);
				tree.setLeaf(false);
				//递归开始 ：节点集合里寻找其parent_id=根节点(上级循环节点)menu_id的节点,设置信息
				tree.setChildren(this.setTree(String.valueOf(menu.getId()),menuList));
				
				if (tree.getChildren().size()==0) {
					tree.setLeaf(true); 
				}else{
					tree.setLeaf(false);
				}
				treeList.add(tree);
			}
		} 
		return treeList;
	}
	
	/**
	 * 通过角色id查询功能表
	 * @param rolId
	 * @return
	 * List<Function>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<Function> queryFunctionByRoleId(String rolId){
		return roleMapper.queryFunctionByRoleId(rolId);
	}
	
	/**
	 * 通过角色id查询角色功能表
	 * @param rolId
	 * @return
	 * List<RoleFunction>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<RoleFunction> queryRoleFunctionByRoleId(Long rolId){
		return roleFunctionMapper.queryRoleFunctionByRoleId(rolId);
	}
	
	/**
	 * 删除角色功能表数据
	 * @param rf
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public void delRoleFunction(RoleFunction rf) {
		roleFunctionMapper.delete(rf);		
	}
	
	/**
	 * 向角色功能表插入数据
	 * @param roleFunction
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public void saveRoleFunction(RoleFunction roleFunction){
		roleFunctionMapper.insert(roleFunction);
	}
	
	/**
	 * 查询角色
	 * @param role
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response getList(Role role) {
		List<Role> rolelist = query(role);
		return Response.success(rolelist);
	}
	
	/**
	 * 根据条件查询集合
	 * @param role
	 * @return
	 * List<Role>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<Role> query(Role role) {
		Example example = new Example(Role.class);
		Criteria criteria =  example.createCriteria();
		criteria.andEqualTo(role);
		return roleMapper.selectByExample(example);
	}
	
	/**
	 * 角色新增/修改
	 * @param role
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response insert(Role role) {
//		Role role = (Role) request.getBody();
		saveOrUpdate(role);
		return Response.success();
	}
	
	/**
	 * 角色新增/修改
	 * @param role
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@Transactional
	public void saveOrUpdate(Role role) {
		if(role.getId()==null){
//			role.setStatus(Constants.DATA_VALID);//设置状态为有效
			roleMapper.insert(role);		
		}else{
			Role oldrole = roleMapper.selectByPrimaryKey(role.getId());
			roleMapper.updateByPrimaryKeySelective(role);
			//覆盖员工表WEB/APP权限
//			if(!role.getSystemCode().equals(oldrole.getSystemCode())){
//				staffService.overWriteSyscode(role.getId(),role.getSystemCode());
//			}
		}
	}
	
	/**
	 * 保存角色功能表
	 * @param obj
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response saveRoleFunction(Object[] obj) {
//		Object[] obj = (Object[]) request.getBody();
		saveOrUpdateRoleFunction(obj);
		return Response.success();
	}
	
	/**
	 * 保存角色功能表
	 * @param obj
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	 @Transactional
	 public void saveOrUpdateRoleFunction(Object[] obj ){
		List<Long> funIds = new ArrayList<Long>();
		if(obj !=null){
			//清空已经存在的角色权限
			List<RoleFunction> rfList = this.queryRoleFunctionByRoleId((Long) obj[1]);
			for(RoleFunction rf: rfList){
				this.delRoleFunction(rf);
			}
			//重新设置新的角色权限
			String[] idstr = obj[0].toString().split("[;]");
			for (String id : idstr) {
				if(StrUtils.isNotEmpty(id)){
					funIds.add(NumberUtils.toLong(id));	
				}
			}						
			for (Long id : funIds) {
				RoleFunction rf = new RoleFunction();
//				rf.setCreateTime(new Date());
				rf.setFuncId(id);
				rf.setRolId(Long.valueOf(obj[1].toString()));
//				rf.setStatus(Constants.DATA_VALID);
				this.saveRoleFunction(rf);
			}
		}
	 }
	 
	 /**
	  * 查询角色列表
	  * @param role
	  * @return
	  * Response
	  * @author liangj@yuminsoft.com
	  * @date  2017年8月2日
	  */
	 public Response getRoleList(Role role) {
			List<Role> rolelist = query(role);
			return Response.success(rolelist);
		}

	public Response changePassword(AdminUser adminUser, String oldPassword,
			String newPassword) {
		String oldPwd = MD5.MD5Encode(oldPassword);
		String newPwd = MD5.MD5Encode(newPassword);

//		List<AdminUser> list = adminUserService.queryAdminUser(adminUser);

		boolean bResult = false;
		if (adminUser.getPassword().equals(oldPwd))
		{
			adminUser.setPassword(newPwd);
			bResult = adminUserService.updatePassword(adminUser) != null ? true : false;
		}
		else {
			bResult = false;
		}
		return bResult ? Response.success() : Response.fail("修改密码失败");
	}
	
	
}