package com.ymkj.smi.manager.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.google.common.collect.Sets;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.service.LoginService;


/**
 * @author     ym10093  
 * @createTime 2016年11月3日 下午4:08:53
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/auth")
public class AuthController {
//	@Autowired
//	private SysLogService sysLogService;// 日志记录Service
//	@Autowired
//    private ServerAdaptor serverAdaptor;
	@Autowired
	private LoginService loginService;
	@Autowired
	private AuthenticationManager myAuthenticationManager;
//	@Autowired
//	private SendSMSService sendSMSService;
	
	@RequestMapping(value =  "/login" )
	public String toLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
//		AdminUser staff = getCurrentUser(request, response, session);
//		if(null == staff){
			return "login";
//		}
//		return "redirect:/index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSubmit(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		//		AdminUser user = getCurrentUser(request, response, session);
//		if(null == user){
//			return "login";
//		}
//		return "redirect:/index";
		AdminUser adminuser = new AdminUser();
		String jno = request.getParameter("jno");
		//String password = MD5.MD5Encode(MD5.MD5Encode(request.getParameter("password")));
		String password = request.getParameter("password");
		if(StringUtil.isEmpty(jno)){
			model.addAttribute("msg", "请输入工号");
			return "login";
		}
		if(StringUtil.isEmpty(password)){
			model.addAttribute("msg", "请输入密码");
			return "login";
		}
		
		adminuser.setJno(jno);
		List<AdminUser> list = new ArrayList<AdminUser>();
		list = loginService.selectParams(adminuser);
		if(list.isEmpty()){
			model.addAttribute("msg", "工号错误请重试!");
			return "login";
		}else{
			//for(AdminUser user : list){
				adminuser = list.get(0);
				String userpass = adminuser.getPassword();
				if(MD5.MD5Encode(MD5.MD5Encode(password)).equals(userpass)){
					HttpSession session = request.getSession(true);
					session.setAttribute(Constants.SYS_LOGIN_USR,adminuser);
					Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminuser.getJno(), adminuser.getPassword()));
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authentication);
					session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext); 
					return "redirect:/admin";
				}else{
					model.addAttribute("msg", "密码错误请重试!");
					return "login";
				}
			//}
			//return "login";
		}
		
		
		
	}
	
//	/**
//	 * 登录
//	 */
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	//@SystemControllerLog(description = "登录",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_LOGIN)
//	public String loginSubmit(HttpServletRequest request, HttpServletResponse res, Model model) {
//		HttpSession session = request.getSession();
//		String mobile = request.getParameter("mobile");//登录手机号
//		String password = request.getParameter("password");//密码
//		String validCode = request.getParameter("validCode");//验证码
//		
//		String remember = request.getParameter("remember"); //是否自动登录
//		//String remember = "1";
//		
//		if(StringUtil.isEmpty(mobile)){
//			model.addAttribute("msg", "手机号不能为空");
//			return "login";
//		}
//		request.setAttribute("mobile", mobile);
//		if(StringUtil.isEmpty(password)){
//			model.addAttribute("msg", "请输入密码");
//			return "login";
//		}
//		if(StringUtil.isEmpty(validCode)){
//			model.addAttribute("msg", "请输入验证码");
//			return "login";
//		}
//		if(!validCode.equalsIgnoreCase(String.valueOf(session.getAttribute("vnum")))){
//			model.addAttribute("msg", "验证码错误");
//			return "login";
//		}
//		//100006 校验员工密码
//		Request param = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100006);
//		Map<String,Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("mobilePhone", mobile);
//		paramMap.put("customerPassword", MD5.MD5Encode(password));
//		param.setBody(paramMap);
//		Response response = serverAdaptor.send(param);//查询员工信息
//		if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(response.getCode())){
//			model.addAttribute("msg", response.getMsg());
//			return "login";
//		}
//		AdminUser staff = (AdminUser) response.getData();
//		String sysCode = staff.getSystemCode();//系统登录限制
//		if(!"1".equals(sysCode) && !"2".equals(sysCode)){//（0：APP；1：WEB；2：所有）
//			model.addAttribute("msg", "无该系统访问权限");
//			return "login";
//		}
//		//加载员工报表权限
////		Request reqst = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100017);
////		reqst.setBody(staff.getId().toString());
////		Response resp = serverAdaptor.send(reqst);
////		if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(resp.getCode())){
////			model.addAttribute("msg", response.getMsg());
////			return "login";
////		}
//		List<StaffDepCode> staffDepCodeList=(List<StaffDepCode>)resp.getData();
//		
//		log.debug("用户【" + mobile + "】登录成功！");
////		sysLogService.save2(com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_LOGIN,"","登录","loginSubmit","登录成功",mobile,staff);
////		
////		session.setAttribute(Constants.SYS_LOGIN_USR, staff);
////		session.setAttribute(Constants.SYS_LOGIN_STAFF_DEP, staffDepCodeList);
////		request.setAttribute("needChangePWD", staff.getNeedChangePwd());
////        
////		/*---spring security-----*/
////		Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(staff.getStaffNo(), staff.getPassword()));
////		SecurityContext securityContext = SecurityContextHolder.getContext();
////		securityContext.setAuthentication(authentication);
////		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);  
////		/*---spring security-----*/
//		AdminUser user = setCurrentUser(request, res, session, staff, remember);
//		if(null == user){
//			model.addAttribute("msg", "系统繁忙，请稍后再试");
//			return "login";
//		}
//		return "redirect:/index";
//	}
	
//	/**
//	 * 登出
//	 */
//	@RequestMapping(value = "/logout")
//	@SystemControllerLog(description = "退出",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_LOGOUT)
//	public String logout(HttpServletRequest req, HttpServletResponse  res) {
//		sysLogService.save1(com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_LOGOUT,"","退出","logout","退出成功");
//		HttpSession session=req.getSession();
//		session.invalidate();
//		return "login";
//	}
	
//	/**
//	 * 修改密码
//	 */
//	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
//	@ResponseBody
//	public ResultVo changePassword(HttpServletRequest req, HttpServletResponse  res){
//		String oldPassword = req.getParameter("oldPassword");//旧密码
//		String newPassword = req.getParameter("newPassword");//新密码
//		Staff staff = (Staff) req.getSession().getAttribute(Constants.SYS_LOGIN_USR);
//		if(!MD5.MD5Encode(MD5.MD5Encode(oldPassword)).equals(staff.getPassword())){
//			return ResultVo.returnMsg(false, "旧密码错误");
//		}
//		Request request = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100007);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("idNo", staff.getCardNo());
//		map.put("oldPwd", MD5.MD5Encode(oldPassword));
//		map.put("newPwd",MD5.MD5Encode(newPassword));
//		request.setBody(map);
//		Response response = serverAdaptor.send(request);
//		if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(response.getCode())){
//			return ResultVo.returnMsg(false, response.getMsg());
//		}
//		return ResultVo.returnMsg(true, "密码修改成功");
//	}
	
	/**
	 * 权限拦截页面
	 */
	@RequestMapping(value = "/denied")
	public String denied(HttpServletRequest req, HttpServletResponse  res){
		return "denied";
	}

	/**
	 * 验证码
	 */
	@RequestMapping(value = "/verifyCodeImage")
	public String verifyCodeImage(HttpServletRequest req, HttpServletResponse  res){
		return "verifyCodeImage";
	}
//	/**
//	 * 发送验证码
//	 * @param model
//	 * @param f_mobile
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = { "sendvfcode" },method= RequestMethod.POST)
//	@ResponseBody 
//	public ResultVo sendVFCode(Model model,String f_mobile, HttpServletRequest request){
//		if(f_mobile==null||"".equals(f_mobile)){
//			return ResultVo.returnMsg(false, "手机号不能为空");
//		}
//		String vfCode = getVFCode();
//		log.info("========手机号========"+f_mobile+",验证码："+vfCode);
//		String content = "您正在找回大拇指助手管理系统的登录密码,本次验证码为：" + vfCode + ",验证码有限时长5分钟。 ";
//		boolean result=sendSMSService.sendSms(f_mobile, content);
//		if(!result){
//			return ResultVo.returnMsg(false, "验证码发送失败,请重试");
//		}
//		//sendSMSService.sendMsg("test", content);
//		HttpSession session=request.getSession();
//		session.setMaxInactiveInterval(5*60);//以秒为单位
//		session.setAttribute(f_mobile, vfCode);
//		return ResultVo.returnMsg(true, "验证码发送成功");
//	}
//	/**
//	 * 校验验证码是否正确
//	 * @param model
//	 * @param f_mobile
//	 * @param vfcode
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = { "checkvfcode" })
//	@ResponseBody 
//	public ResultVo checkVFCode(Model model, HttpServletRequest request){
//		String mobile=request.getParameter("mobile_");
//		String vfcode=request.getParameter("vfcode");
//		HttpSession session=request.getSession();
//		if(session.getAttribute(mobile)!=null&&vfcode.equals(session.getAttribute(mobile).toString())){			
//			session.removeAttribute(mobile);
//			return ResultVo.returnMsg(true, "success");
//		}
//		return ResultVo.returnMsg(false, "验证码不正确");		
//	}
//	/**
//	 * 保存找回的密码
//	 * @param model
//	 * @param newpassword
//	 * @param mobile
//	 * @return
//	 */
//	@RequestMapping(value = { "saveNewPwd" })
//	@ResponseBody 
//	public ResultVo saveNewPwd(Model model,String newpassword,String mobile){
//		Request request = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100014);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("mobile", mobile);
//		map.put("newPwd",MD5.MD5Encode(MD5.MD5Encode(newpassword)));
//		request.setBody(map);
//		Response response = serverAdaptor.send(request);
//		if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(response.getCode())){
//			return ResultVo.returnMsg(false, response.getMsg());
//		}
//		return ResultVo.returnMsg(true, "找回密码成功");
//	}
	
	String getVFCode(){
		Random ran= new Random();	
		String code =(ran.nextInt(9000)+1000)+"";		
		return code;
	}
	
//	/**
//	 * 自动登录-设置当前登录用户
//	 * 
//	 * @param request
//	 * @param response
//	 * @param staff
//	 * @param remember
//	 */
//	public AdminUser setCurrentUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, Staff staff, String remember){
//		try {
//			//加载员工报表权限
//			Request reqst = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100017);
//			reqst.setBody(staff.getId().toString());
//			Response resp = serverAdaptor.send(reqst);
//			if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(resp.getCode())){
//				log.error("登录成功后加载权限查询异常：" + resp.getMsg());
//				return null;
//			}
//			List<StaffDepCode> staffDepCodeList=(List<StaffDepCode>)resp.getData();
//			
//			session.setAttribute(Constants.SYS_LOGIN_USR, staff);
//			session.setAttribute(Constants.SYS_LOGIN_STAFF_DEP, staffDepCodeList);
//			request.setAttribute("needChangePWD", staff.getNeedChangePwd());
//	        
//			/*---spring security-----*/
//			Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(staff.getStaffNo(), staff.getPassword()));
//			SecurityContext securityContext = SecurityContextHolder.getContext();
//			securityContext.setAuthentication(authentication);
//			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);  
//			/*---spring security-----*/
//			
//			//用户登录cookie
//			if("1".equals(remember)){
//				String cxtPath = request.getContextPath();
//				if(cxtPath == null || cxtPath.isEmpty()){
//					cxtPath = "/";
//				}
//				// 1.设置cookie有效时间
//				int maxAgeTemp = Integer.parseInt(PropertiesUtil.getValue("config_maxAge_key"));
//	
//				// 2.设置用户名到cookie
//				ToolWeb.addCookie(response, "", cxtPath, true, "userName", staff.getMobile(), maxAgeTemp);
//	
//				// 3.生成登陆认证cookie
//				String mobile = staff.getMobile();
//				String ips = ToolWeb.getIpAddr(request);
//	//				String ips = "127.0.0.1";
//				String userAgent = request.getHeader("User-Agent");
//				long date = new Date().getTime();
//				
//				StringBuilder token = new StringBuilder();// 时间戳.#.MOBILE.#.USER_IP.#.USER_AGENT.#.true
//				token.append(date).append(".#.").append(mobile).append(".#.").append(ips).append(".#.").append(userAgent).append(".#.").append(true);
//				String authmark = ToolIDEA.encrypt(token.toString());
//				
//				// 4. 添加到Cookie和header
//				ToolWeb.addCookie(response,  "", cxtPath, true, com.ymkj.thumb.assistant.manager.constants.Constants.cookie_authmark, authmark, maxAgeTemp);
//				response.setHeader(com.ymkj.thumb.assistant.manager.constants.Constants.cookie_authmark, authmark);
//			}
//			return staff;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	/**
//	 * 获取当前登录用户
//	 * 
//	 * @param request
//	 * @param response
//	 * @param session
//	 * @return
//	 */
//	public AdminUser getCurrentUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		String cxtPath = request.getContextPath();
//		if(cxtPath == null || cxtPath.isEmpty()){
//			cxtPath = "/";
//		}
//		
//		// 加密串存储位置，默认先取header
//		String loginCookie = request.getHeader(com.ymkj.thumb.assistant.manager.constants.Constants.cookie_authmark);
//		if(loginCookie == null || loginCookie.isEmpty()){
//			// 如果为空，再取cookie
//			loginCookie = ToolWeb.getCookieValueByName(request, com.ymkj.thumb.assistant.manager.constants.Constants.cookie_authmark);
//		}
//		
//		// 处理加密串的解析和验证
//		if (null != loginCookie && !loginCookie.equals("")) {
//			// 1.解密认证数据
//			String data = ToolIDEA.decrypt(loginCookie);
//			if(null == data || data.isEmpty()){
//				return null;
//			}
//			String[] datas = data.split(".#.");	//arr[0]：时间戳，arr[1]：USERID，arr[2]：USER_IP， arr[3]：USER_AGENT
//			
//			// 2. 分解认证数据
//			long loginDateTimes;
//			String mobile = null;
//			try {
//				loginDateTimes = Long.parseLong(datas[0]); // 时间戳
//				mobile = datas[1]; // 登录手机号
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//			
//			// 3.用户当前数据
//			Date start = new Date();
//			start.setTime(loginDateTimes); // 用户自动登录开始时间
//			int day = DateUtil.getIntervalDays(start, new Date()); // 已经登录多少天
//			int maxAge = Integer.parseInt(PropertiesUtil.getValue("config_maxAge_key")) / (60 * 60 * 24); //保持登录天数
//			
//			// 4. 验证数据有效性
//			if (day <= maxAge) {
//				//查询staff 100016
//				Request reqst = Request.create(Constants.SYS_MANAGER, FunctionNo.FUNCTIONID_100016);
//				Staff req = new Staff();
//				req.setMobile(mobile);
//				req.setStatus(Constants.DATA_VALID);
//				req.setQryStaffStatus(Sets.newHashSet(new String[]{Constants.STAFF_STATUS_ALREADY_ENTRY, Constants.STAFF_WAIT_QUIT}));//查询在职员工
//				reqst.setBody(req);
//				Response resp = serverAdaptor.send(reqst);
//				if(!com.ymkj.thumb.assistant.api.constants.Constants.CODE_SUCCESS.equals(resp.getCode())){
//					log.error("自动登录查询用户异常：" + resp.getMsg());
//					return null;
//				}
//				if(null == resp.getData()){
//					return null;
//				}
//				Staff staff = ((List<Staff>) resp.getData()).get(0);
//				return setCurrentUser(request, response, session, staff, null);
//			}
//		}
//		return null;
//	}
}
