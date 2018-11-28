package com.ymkj.smi.manager.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.AdminUserRole;
import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.mapper.AdminUserMapper;
import com.ymkj.smi.manager.web.api.dto.AdminDTO;
import com.ymkj.smi.manager.web.api.dto.CustomerDTO;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_001001;
import com.ymkj.smi.manager.web.api.model.base.Model_001003;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;

/**
* AdminUserService
* <p/>
* Author: liangj@yuminsoft.com
* Date: 2017-07-24 18:31:38
* Mail: 
*/
@Service
public class AdminUserService  {

	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AdminUserRoleService adminUserRoleService;
	/**
	 * 
	 * @TODO 收费员下拉列表
	 * @return
	 * List<Map<String,Object>>
	 * @author changj@yuminsoft.com
	 * @date2017年7月26日
	 */
	public List<Map<String,Object>>  getCashierForOption(){
		Example example=new Example(AdminUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		criteria.andEqualTo("eType", Constants.CASHIER);//收费员
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		if(list!=null && list.size()>0){
			for(AdminUser ws:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",ws.getId());
				map.put("tollCollector",ws.getName()+"_"+ws.getPhone());
				retList.add(map);
			}
		}
		return retList;
	}
	

	/**
	 * 员工分页查询
	 * @param pageInfo
	 * @return
	 * PageInfo<AdminUser>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public PageInfo<AdminUser> getAdminUserPage(PageInfo<AdminUser> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		AdminUser user = (AdminUser) pageInfo.getQueryParam();
        Page<AdminUser> page = (Page<AdminUser>)query(user);
        return PageUtils.convertPage(page);
	}
	
	/**
	 * 根据条件查询员工
	 * @param user
	 * @return
	 * List<AdminUser>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<AdminUser> query(AdminUser user) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(user.getName())){
			map.put("name",user.getName()); 
		}
		if(StringUtils.isNotBlank(user.getJno())){
			map.put("jno",user.getJno()); 
		}
		if(StringUtils.isNotBlank(user.getStatus())){
			map.put("status",user.getStatus()); 
		}
		return adminUserMapper.getAdminUserList(map);
	}
	
	/**
	 * 员工查询
	 * @param user
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response getList(AdminUser user) {
		List<AdminUser> rolelist = queryAdminUser(user);
		return Response.success(rolelist);
	}
	

	/**
	 * 根据条件查询集合
	 * @param user
	 * @return
	 * List<AdminUser>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<AdminUser> queryAdminUser(AdminUser user) {
		Example example = new Example(AdminUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(user);
		return adminUserMapper.selectByExample(example);
	}
	
	/**
	 * 角色新增/编辑
	 * @param user
	 * @return
	 * Response
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public Response insert(AdminUser user) {
		saveOrUpdate(user);
		return Response.success();
	}
	
	/**
	 * 通过工号查询员工
	 * @param jno
	 * @return
	 * AdminUser
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public AdminUser getAdminUser(String jno){
		AdminUser adminUser =null;
		Example example = new Example(AdminUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("jno", jno);
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		if(list.size()>0){
			adminUser = list.get(0);
			return adminUser;
		}
		return null;
	}
	
	/**
	 * 角色新增/编辑
	 * @param adminUser
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@Transactional
	public void saveOrUpdate(AdminUser adminUser) {
		if(adminUser.getId()==null){
//			adminUser.setStatus(Constants.DATA_VALID);//设置状态为有效
			adminUser.setPassword(MD5.MD5Encode(MD5.MD5Encode(Constants.DEFAULT_PASSWORD)));
			adminUserMapper.insert(adminUser);	
			AdminUser adminUser1 = getAdminUser(adminUser.getJno());
			adminUser.setId(adminUser1.getId());
		}else{
//			AdminUser oldUser = adminUserMapper.selectByPrimaryKey(user.getId());
			adminUserMapper.updateByPrimaryKeySelective(adminUser);

		}
		if(!adminUserRoleService.checkAdminUserRole(adminUser.getId())&&null!=adminUser.getRolId()){
			AdminUserRole adminUserRole = new AdminUserRole();
			adminUserRole.setAdmId(adminUser.getId());
			adminUserRole.setRolId(adminUser.getRolId());
			adminUserRoleService.insert(adminUserRole);
		}else if(adminUserRoleService.checkAdminUserRole(adminUser.getId())&&null!=adminUser.getRolId()){
			AdminUserRole adminUserRole = adminUserRoleService.getAdminUserRole(adminUser.getId());
			adminUserRole.setAdmId(adminUser.getId());
			adminUserRole.setRolId(adminUser.getRolId());
			adminUserRoleService.updateAdminUserRole(adminUserRole);
		}else if(adminUserRoleService.checkAdminUserRole(adminUser.getId())&&null==adminUser.getRolId()){
			AdminUserRole adminUserRole = adminUserRoleService.getAdminUserRole(adminUser.getId());
//			adminUserRole.setAdmId(user.getId());
//			adminUserRole.setRolId(Long.parseLong(user.getRoleName()));
			adminUserRoleService.deleAdminUserRole(adminUserRole.getId());
		}
	}


	/**
	 * 更新密码
	 * @param adminUser
	 * @return
	 * AdminUser
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public AdminUser updatePassword(AdminUser adminUser) {
		int i = adminUserMapper.updateByPrimaryKeySelective(adminUser);
		return i > 0 ? adminUser : null;
	}
	/**
	 * 登录(作业船APP)
	 * @author huangsy@yuminsoft.com
	 */
	public Result adminLogin(Model_001001 model){
		String jno = model.getJno();//工号
		String password = model.getPassword();//密码：一次MD5加密
		
		AdminUser adminUser = new AdminUser();
		adminUser.setJno(jno);
		List<AdminUser> list = queryAdmin(adminUser);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("工号不存在");
		}
		adminUser = list.get(0);
		if(!MD5.MD5Encode(password).equals(adminUser.getPassword())){
			return Result.fail("密码错误");
		}
		AdminDTO dto = new AdminDTO();
		dto.setId(adminUser.getId());
		dto.setJno(adminUser.getJno());
		dto.setName(adminUser.getName());
		dto.setPhone(adminUser.getPhone());
		return Result.success(dto);
	}
	/**
	 * 查询用户
	 */
	public List<AdminUser> queryAdmin(AdminUser adminUser){
		Example example=new Example(AdminUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		if(StringUtils.isNotBlank(adminUser.getJno())){//
			criteria.andEqualTo("jno", adminUser.getJno());
		}
		
		return adminUserMapper.selectByExample(example);
	}

/**
 * 作业船修改密码
 * @param model
 * @return
 * Result
 * @author huangsy@yuminsoft.com
 * @date  2017年8月4日
 */
	public Result adminUpdatePwd(Model_001003 model) {
		// TODO Auto-generated method stub
		String jno = model.getJno();//用户名
		String password = model.getOldPassword();//密码：一次MD5加密
		
		AdminUser adminUser = new AdminUser();
		adminUser.setJno(jno);
		List<AdminUser> list = queryAdmin2(adminUser);
		if(CollectionUtils.isEmpty(list)){
			return Result.fail("工号不存在");
		}
		adminUser = list.get(0);
		if(!MD5.MD5Encode(password).equals(adminUser.getPassword())){
			return Result.fail("密码错误");
		}else{
			adminUser.setPassword(MD5.MD5Encode(model.getNewPassword()));
			adminUserMapper.updateByPrimaryKeySelective(adminUser);
			return Result.success("修改成功");
		}
	}
	
	public List<AdminUser> queryAdmin2(AdminUser adminUser){
		Example example=new Example(AdminUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", Constants.DATA_VALID);
		if(StringUtils.isNotBlank(adminUser.getJno())){//
			criteria.andEqualTo("jno", adminUser.getJno());
		}
		//criteria.andEqualTo("eType","2");
		return adminUserMapper.selectByExample(example);
	}
	
	/**
	 * 功能描述：
	 * 输入参数：
	 * @param id
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年8月15日
	 */
	public Response resetPwd(Long id){
		Response resp = Response.success();
		if(null == id){
			resp.setCode(Constants.CODE_FAILURE);
			resp.setCode("ID为空");
			return resp;
		}
		AdminUser user = new AdminUser();
		user.setId(id);
		user.setStatus(Constants.DATA_VALID);
		AdminUser record = adminUserMapper.selectOne(user);
		if(null == record){
			resp.setCode(Constants.CODE_FAILURE);
			resp.setCode("对应ID记录不存在");
			return resp;
		}
		record.setPassword(MD5.MD5Encode(MD5.MD5Encode(Constants.DEFAULT_PASSWORD)));
		record.setUpdateTime(new Date());
		adminUserMapper.updateByPrimaryKey(record);
		return resp;
	}
	
}