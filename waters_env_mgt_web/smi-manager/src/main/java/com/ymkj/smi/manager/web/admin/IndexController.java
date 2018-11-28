package com.ymkj.smi.manager.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.common.entity.MenuTreeModel;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.RoleService;
import com.ymkj.springside.modules.utils.Response;


/**
 * 首页
 * @author YM10147
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class IndexController{
	
	@Autowired
    private RoleService  roleService;

	
	@RequestMapping(value = { "" })
	public String index(Model model,HttpSession httpSession) {
		return "index";
	}

	@RequestMapping("login")
	public String login(Model model, ServletRequest request) {
		return "login";
	}
	
	
	@RequestMapping("loginSuccess")
	public String loginSuccess(Model model, ServletRequest request,HttpSession httpSession) {
		System.out.println("登录成功");
		return "index";
	}

	@RequestMapping("logout")
	public String logout(Model model, ServletRequest request,HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/admin/auth/login";
	}

	/**
	 * 加载左侧菜单
	 * @param model
	 * @param request
	 * @return
	 * String
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping(value = { "left" })
	public String left(Model model, HttpServletRequest request) {
		String parentNode = StringUtils.isNotBlank(request.getParameter("parentNode"))?request.getParameter("parentNode"):"0";
		List<Function> funcs = getFunction(request, parentNode);
		
		List<Function> funcs_=new ArrayList<Function>();
		for(Function f:funcs){
			if(!"首页".equals(f.getFName())){
				funcs_.add(f);
			}
		}
		model.addAttribute("menus", funcs_);
		return "index_left";
	}

	/**
	 * 左侧菜单下的子菜单
	 * @param model
	 * @param request
	 * @return
	 * List<MenuTreeModel>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping(value = { "showLeftSubMenus" })
	@ResponseBody 
	public List<MenuTreeModel> showLeftSubMenus(Model model, HttpServletRequest request){
		String parentNode = StringUtils.isNotBlank(request.getParameter("parentNode"))?request.getParameter("parentNode"):"0";
		return setTree(request, parentNode, getFunction(request, parentNode));
	}

	/**
	 * 返回某个节点下的功能菜单
	 * @param request
	 * @param parentNode
	 * @return
	 * List<Function>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	private List<Function> getFunction(HttpServletRequest request, String parentNode) {
		List<Function> funcs = new ArrayList<Function>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(Constants.SYS_LOGIN_USR);
		AdminUser queryUser = new AdminUser();
		queryUser.setId(user.getId());
		queryUser.setParentNode(parentNode);
		
		log.debug("返回某个节点下的功能菜单 入参：" + queryUser .toString());
		Response response = roleService.getFunctionByCondtion(queryUser);//查询菜单
		log.debug("返回某个节点下的功能菜单 出参：" + response.toString());
		if(!Constants.CODE_SUCCESS.equals(response.getCode())){
			//TODO:ERROE PAGE
			log.error("查询菜单出错：" + response.getMsg());
			return funcs;
		}
		funcs = (List<Function>) response.getData();
		return funcs;
	}
	
	/**
	 * 加载菜单树
	 * @param request
	 * @param parentCode
	 * @param funcs
	 * @return
	 * List<MenuTreeModel>
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public  List<MenuTreeModel> setTree(HttpServletRequest request, String parentCode,List<Function> funcs) {
		List<MenuTreeModel> treeList = new ArrayList<MenuTreeModel>();
		for (Function func : funcs) {
			MenuTreeModel tree = new MenuTreeModel();
			tree.setId(String.valueOf(func.getId()));
			tree.setText(func.getFName());
			Map<String, Object> attributes=new HashMap<String, Object>();
			attributes.put("url", func.getUrl());
			tree.setAttributes(attributes);
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 加载右侧内容
	 * @param model
	 * @param httpSession
	 * @param request
	 * @return
	 * String
	 * @author liangj@yuminsoft.com
	 * @date  2017年8月2日
	 */
	@RequestMapping(value = { "right" })
	public String right(Model model,HttpSession httpSession,HttpServletRequest request) {


		return "index_right";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo changePassword(HttpServletRequest req, HttpServletResponse  res){
		String oldPassword = req.getParameter("oldPassword");//旧密码
		String newPassword = req.getParameter("newPassword");//新密码
		AdminUser adminUser = (AdminUser) req.getSession().getAttribute(Constants.SYS_LOGIN_USR);
		if(!MD5.MD5Encode(MD5.MD5Encode(oldPassword)).equals(adminUser.getPassword())){
			return ResultVo.returnMsg(false, "旧密码错误");
		}
//		Request request = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100007);
		Map<String, Object> map = new HashMap<String, Object>();
		oldPassword =  MD5.MD5Encode(oldPassword);
		newPassword = MD5.MD5Encode(newPassword);
//		request.setBody(map);
		Response response = roleService.changePassword(adminUser,oldPassword,newPassword);
		if(!Constants.CODE_SUCCESS.equals(response.getCode())){
			return ResultVo.returnMsg(false, response.getMsg());
		}
		return ResultVo.returnMsg(true, "密码修改成功");
	}
	



}


