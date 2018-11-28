package com.ymkj.smi.manager.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.MenuTreeModel;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.RoleService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;




/**
* RoleController
* <p/>
* Author: liangj@yuminsoft.com
* Date: 2017-07-24 18:32:05
* Mail: 
*/
@Controller
@RequestMapping("/admin/role")
public class RoleController  {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("list")
	public String roleList() {
		return "sys/roleList";
	}
	
	/**
	 * 角色新增/编辑
	 * @param role
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("save")
	@ResponseBody
	public ResultVo saveRole(Role role){
		Response resp = null;
		try{
		  //新增的时候判断角色编号是否已经存在
		  if(role.getId()==null){
				Role  queryRole = new Role();
				queryRole.setRoleCode(role.getRoleCode());
				Response queryResp = roleService.getList(queryRole);
				List<Role> roleLsit = (List<Role>) queryResp.getData();
				if(roleLsit.size() > 0){
					return  new ResultVo(null, 0, Boolean.FALSE, "角色编号已存在，请重新输入");
				}
				role.setCreateTime(new Date());
		   }else{
			    role.setUpdateTime(new Date());
		   }
//		   logger.info("角色新增/编辑请求消息内容："+req);
		   resp = roleService.insert(role);
//		   logger.info("角色新增/编辑响应消息内容："+resp);
		} catch (Exception e) { 
			e.getStackTrace();
		}
		return  ResultVo.returnMsg(resp);
	}

	/**
	 * 校验角色编号唯一性
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("checkroleCode")
	@ResponseBody
	public ResultVo checkRoleNo(String roleCode){
		Response resp = null;
		try{
		   //新增的时候判断角色编号是否已经存在
			Role  queryRole = new Role();
			queryRole.setRoleCode(roleCode);
			Response queryResp = roleService.getList(queryRole);
			List<Role> roleLsit = (List<Role>) queryResp.getData();
			if(roleLsit.size() > 0){
 				return  new ResultVo(null, 0, Boolean.FALSE, "角色编号已存在，请重新输入");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return  new ResultVo(null, 0, Boolean.TRUE, null);
	}


	/**
	 * 角色列表分页
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param request
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("rolePage")
	@ResponseBody
	public ResultVo rolePage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			HttpServletRequest request) {
		
			String roleName = StrUtils.isNotBlank(request.getParameter("roleName"))?request.getParameter("roleName"):"";
			String roleCode  = StrUtils.isNotBlank(request.getParameter("roleCode"))?request.getParameter("roleCode"):"";
			String status = StrUtils.isNotBlank(request.getParameter("status"))?request.getParameter("status"):"";
			
		
		
			PageInfo<Role> requestbody = new PageInfo<Role>();
			Role role = new Role();
			
			role.setRoleName(roleName);
			role.setRoleCode(roleCode);
			role.setStatus(status);
			
			requestbody.setPageNo(page);
			requestbody.setPageSize(rows);
			requestbody.setQueryParam(role);
			requestbody = roleService.getPage(requestbody);
			return ResultVo.returnPage(requestbody);	
	}
	
	@RequestMapping("role_function")
	public String forwardToRoleFunction(@RequestParam(value = "rolId") String rolId, Model model) {
		model.addAttribute("rolId", rolId);
		return "sys/role_function";
	}

	/**
	 * 获取权限菜单
	 * @param rolId
	 * @return
	 * Object
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRoleMenu")
	@ResponseBody
	public Object getRoleMenu(@RequestParam(value = "rolId") String rolId) {
		List<MenuTreeModel> treeList=new ArrayList<MenuTreeModel>();
		try {
			Response resp = roleService.getRoleMenu(rolId);
			treeList=(List<MenuTreeModel>) resp.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeList;
	}
	
	/**
	 * 保存角色权限
	 * @param req
	 * @return
	 * @throws Exception
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("save_role_function")
	@ResponseBody
	public ResultVo saveRoleFun(HttpServletRequest req) throws Exception {
		Response resp = null;
		String functionIds = req.getParameter("functionIds");
		Long rolId = Long.valueOf(req.getParameter("rolId"));
		Object[] obj = {functionIds, rolId};
		try{
//		   logger.info("添加角色权限请求消息内容："+reqst);
		   resp = roleService.saveRoleFunction(obj);
//		   logger.info("添加角色权限响应消息内容："+resp);
		} catch (Exception e) {
			e.getStackTrace();
		}
		   return  ResultVo.returnMsg(resp);
	}

	
	/**
	 * 获取所有有效角色列表
	 * @param req
	 * @param res
	 * @return
	 * List<Role>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public List<Role> getRoleList(HttpServletRequest req, HttpServletResponse  res){
		Role role = new Role();
		role.setStatus(Constants.DATA_VALID); 
		Response response = roleService.getRoleList(role);
		if(Constants.CODE_SUCCESS.equals(response.getCode())){
			return (List<Role>) response.getData();
		}
		return  null;
	}

    
}