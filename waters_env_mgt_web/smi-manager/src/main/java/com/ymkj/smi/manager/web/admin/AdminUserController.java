package com.ymkj.smi.manager.web.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.AdminUserService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;


/**
* AdminUserController
* <p/>
* Author: liangj@yuminsoft.com
* Date: 2017-07-26 18:31:38
* Mail: 
*/
@Controller
@RequestMapping("/admin/adminUser")
public class AdminUserController{

	
	@Autowired
	private AdminUserService adminUserService;
	
	
	
	@RequestMapping("")
	public String AdminUserList() {
		return "sys/adminUser_list";
	}
	
	
	

	/**
	 * 校验用户编号唯一性
	 * @param jno
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("checkJno")
	@ResponseBody
	public ResultVo checkRoleNo(String jno){
		Response resp = null;
		try{
		   //新增的时候判断用户编号是否已经存在
			AdminUser queryUser = new AdminUser();
			queryUser.setJno(jno);
			Response queryResp = adminUserService.getList(queryUser);
			List<AdminUser> userLsit = (List<AdminUser>) queryResp.getData();
			if(userLsit.size() > 0){
				return  new ResultVo(null, 0, Boolean.FALSE, "工号已存在，请重新输入");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return  new ResultVo(null, 0, Boolean.TRUE, null);
	}
	
//	/**
//	 * 校验用户编号唯一性
//	 * @param jno
//	 * @return
//	 * ResultVo
//	 * @author liangj@yuminsoft.com
//	 * @date  2017年8月2日
//	 */
//	@RequestMapping("checkName")
//	@ResponseBody
//	public ResultVo checkUserName(String name){
//		Response resp = null;
//		try{
//		   //新增的时候判断用户编号是否已经存在
//			AdminUser queryUser = new AdminUser();
//			queryUser.setName(name);
//			Response queryResp = adminUserService.getList(queryUser);
//			List<AdminUser> userLsit = (List<AdminUser>) queryResp.getData();
//			if(userLsit.size() > 0){
//				return  new ResultVo(null, 0, Boolean.FALSE, "用户姓名已存在，请重新输入");
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		return  new ResultVo(null, 0, Boolean.TRUE, null);
//	}
	
	/**
	 * 角色新增/编辑
	 * @param user
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping("save")
	@ResponseBody
	public ResultVo saveAdminUser(AdminUser user,String etype){
		if(StringUtils.isNotBlank(etype)){
			user.setEType(etype);
		}
		Response resp = null;
		try{
		  //新增的时候判断用户工号是否已经存在
		  if(user.getId()==null){
			    AdminUser queryUser = new AdminUser();
				queryUser.setJno(user.getJno());
				Response queryResp = adminUserService.getList(queryUser);
				List<AdminUser> userLsit = (List<AdminUser>) queryResp.getData();
				if(userLsit.size() > 0){ 
					return  new ResultVo(null, 0, Boolean.FALSE, "工号已存在，请重新输入");
				}
				
//				AdminUser queryUser1 = new AdminUser();
//				queryUser1.setName(user.getName());
//				Response queryResp1 = adminUserService.getList(queryUser1);
//				List<AdminUser> userLsit1 = (List<AdminUser>) queryResp1.getData();
//				if(userLsit1.size() > 0){ 
//					return  new ResultVo(null, 0, Boolean.FALSE, "用户姓名已存在，请重新输入");
//				}
				user.setCreateTime(new Date());
		   }else{
			    user.setUpdateTime(new Date());
		   }
//		   logger.info("用户新增/编辑请求消息内容："+req);
		   resp = adminUserService.insert(user);
//		   logger.info("用户新增/编辑响应消息内容："+resp);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return  ResultVo.returnMsg(resp);
	}
	
	/**
	 * 按条件分页查询.
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param model
	 * @param request
	 * @return
	 * ResultVo
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public ResultVo list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "") String sort,
			@RequestParam(value = "order", defaultValue = "") String order,
			Model model, ServletRequest request) {
		AdminUser user = new AdminUser();
		formatSearch(request, user);
		PageInfo<AdminUser> body = new PageInfo<AdminUser>();
		body.setPageNo(page);
		body.setPageSize(rows);
		body.setQueryParam(user);
		body = adminUserService.getAdminUserPage(body);
		return ResultVo.returnPage(body!=null ? body : new PageInfo());		
	}


	/**
	 * 构造查询条件
	 * @param request
	 * @param user
	 * void
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public void formatSearch(ServletRequest request, AdminUser user) {
		if(StrUtils.isNotBlank(request.getParameter("jno")))
			user.setJno(request.getParameter("jno"));
		if(StrUtils.isNotBlank(request.getParameter("name")))
			user.setName(request.getParameter("name"));
		if(StrUtils.isNotBlank(request.getParameter("status")))
			user.setStatus(request.getParameter("status"));
	}
	
	/**
	 * 功能描述：用户密码重置
	 * 输入参数：
	 * @param id
	 * @return
	 * 返回类型：Response
	 * 创建人：tianx
	 * 日期：2017年8月15日
	 */
	@RequestMapping(value="/resetPwd")
	@ResponseBody
	public Response resetPwd(Long id){
		Response resp = adminUserService.resetPwd(id);
		return  resp;
	}
}