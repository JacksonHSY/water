package com.ymkj.smi.web.web;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.web.service.AnnouncementService;
import com.ymkj.smi.web.service.CustomerService;
import com.ymkj.smi.web.service.ValidateCodeService;
import com.ymkj.smi.web.utils.Constants;
import com.ymkj.smi.web.utils.MD5;
import com.ymkj.smi.web.utils.Response;
import com.ymkj.smi.web.utils.StrUtils;
import com.ymkj.smi.web.vo.Customer;


/**
 * @Description：对类进行描述
 * @ClassName: AuthController.java
 * @Author：tianx
 * @Date：2017年8月14日
 * -----------------变更历史-----------------
 * 如：who  2017年8月14日  修改xx功能
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private ValidateCodeService validateCodeService;
	@RequestMapping(value =  "/login" )
	public String toLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		//公告
		String s=announcementService.getAnnouncementList(1,5);
		JSONObject resultJson = JSONObject.parseObject(s);
		if(null != resultJson && resultJson.get("code").equals("0000")){
			model.addAttribute("msg", resultJson.get("msgEx"));
		}	
		return "login";
	}
	
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Response loginSubmit(HttpServletRequest request, HttpServletResponse response,Model model) {
		HttpSession session = request.getSession(true);
		String loginName = request.getParameter("username");
		String password = request.getParameter("password");
		String valid = request.getParameter("valid");
		Response res = Response.success(); 
		if(StrUtils.isEmpty(loginName)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("用户名不能为空");
			return res;
		}
		if(StrUtils.isEmpty(password)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入密码");
			return res;
		}
		if(StrUtils.isEmpty(valid)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入验证码");
			return res;
		}
		
		String resultString = customerService.login(loginName, password);
		if(StringUtils.isNotBlank(resultString)){
		      JSONObject resultJson = JSONObject.parseObject(resultString);
		      if(null != resultJson && resultJson.get("code").equals("0000")){
		    	  JSONObject resJson=  (JSONObject) resultJson.get("msgEx");
		    	  if(("0").equals(resJson.get("status"))){
						JSONObject userInfo = JSONObject.parseObject(resJson.getString("infos"));
		    		  	Customer customer = new Customer();
		    		  	customer.setId(userInfo.getLong("id"));	;
			    	  	customer.setUserName(loginName);
			    	  	customer.setPassword(password);
						session.setAttribute(Constants.WEB_LOGIN_USR,customer);
						if(!valid.equalsIgnoreCase(String.valueOf(session.getAttribute("vnum")))){
							res.setCode(Constants.CODE_FAILURE);
							res.setMsg("验证码错误");
							return res;
						}
		    	  }
					res.setData(resultJson.get("msgEx"));
					return res;
		      }else{
		    	  res.setCode(Constants.CODE_FAILURE);
		    	  res.setMsg("");
		    	  return res;
		      }
		    }
		return res;
	}
	
	
	/**
	 * 验证码
	 */
	@RequestMapping(value = "/verifyCodeImage")
	public String verifyCodeImage(HttpServletRequest req, HttpServletResponse  res){
		return "verifyCodeImage";
	}
	
	/**
	 * 注册
	 */
	@RequestMapping(value = "/doRegiest", method = RequestMethod.POST)
	@ResponseBody
	public Response doRegiest(HttpServletRequest request, HttpServletResponse response,Model model) {
		HttpSession session = request.getSession(true);
		String phone = request.getParameter("phone");
		String vaildateCode = request.getParameter("valid01");
		String type = request.getParameter("type");
		String password = request.getParameter("password");
		Response res = Response.success(); 
		if(StrUtils.isEmpty(phone)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入手机号");
			return res;
		}
		if(StrUtils.isEmpty(vaildateCode)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入验证码");
			return res;
		}
		//检验验证码是否正确
		String resultString01 = validateCodeService.validateSelect(phone, type, vaildateCode);
		if(StringUtils.isNotBlank(resultString01)){
			JSONObject resultJson = JSONObject.parseObject(resultString01);
			String resultJson1 = JSONObject.toJSONString(resultJson.get("msgEx"));
			JSONObject object = JSONObject.parseObject(resultJson1);
			if(null != resultJson && "0000".equals(resultJson.get("code")) && "0".equals(object.get("status"))){
				//注册用户
				String resultString = customerService.regiestCustomer(phone,password);
				if(StringUtils.isNotBlank(resultString)){
				      JSONObject resultJson2 = JSONObject.parseObject(resultString);
				      if(null != resultJson2 && resultJson2.get("code").equals("0000")){
				  		String resultJson3 = JSONObject.toJSONString(resultJson2.get("msgEx"));
						JSONObject object2 = JSONObject.parseObject(resultJson3);
						if("0".equals(object2.get("status"))){
							String resultString5 = customerService.login(phone, password);
							if(StringUtils.isNotBlank(resultString)){
							      JSONObject resultJson5 = JSONObject.parseObject(resultString5);
							      if(null != resultJson5 && resultJson5.get("code").equals("0000")){
							    	  JSONObject resJson5=  (JSONObject) resultJson5.get("msgEx");
							    	  if("0".equals(resJson5.get("status"))){
											JSONObject userInfo = JSONObject.parseObject(resJson5.getString("infos"));
							    		  	Customer customer = new Customer();
							    		  	customer.setId(userInfo.getLong("id"));	;
								    	  	customer.setUserName(phone);
								    	  	customer.setPassword(password);
											session.setAttribute(Constants.WEB_LOGIN_USR,customer);
							    	  }
							    	  	res.setCode(Constants.CODE_SUCCESS);	
										res.setData(resultJson.get("msgEx"));
										return res;
							      }else{
							    	  res.setCode(Constants.CODE_FAILURE);
							    	  res.setMsg("");
							    	  return res;
							      }
							    }
						}else{
							res.setCode(Constants.CODE_FAILURE);
							res.setData(resultJson3);
							return res;
						}
				      }else{
				    	  res.setCode(Constants.CODE_FAILURE);
				    	  res.setMsg("error");
				    	  return res;
				      }
				    }
			}else{
				res.setCode(Constants.CODE_FAILURE);
				res.setData(resultJson.get("msgEx"));
			}
		}
		return res;
	}
	
	@RequestMapping("/modifyPassword")
	@ResponseBody
	public Response modifyPassword(HttpServletRequest request,HttpSession httpSession,HttpServletResponse response,Model model) {
		
		Response res = Response.success();
		String oldPassword =request.getParameter("oldpassword");
		String newPassword = request.getParameter("password");
 		Customer customer = (Customer) httpSession.getAttribute(Constants.WEB_LOGIN_USR);
		String resultString = customerService.updateCustomerPwd(customer.getUserName(), MD5.MD5Encode(oldPassword) ,MD5.MD5Encode(newPassword));
		JSONObject resultJson = JSONObject.parseObject(resultString);
		res.setData(resultJson.get("msgEx"));
		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.WEB_LOGIN_USR); 
		return res;
	}
	
	/**
	 * 保存验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * Response
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月28日
	 */
	@RequestMapping(value = "/sendvfcode",method= RequestMethod.POST)
	@ResponseBody
	public Response sendValidate(HttpServletRequest request, HttpServletResponse response,Model model){
		String mobile = request.getParameter("f_mobile");
		String type = request.getParameter("type");
		//判断手机号是否已经存在
		String result = customerService.selectCustomer(mobile);
		JSONObject resultJso = JSONObject.parseObject(result);
		String resultJson1 = JSONObject.toJSONString(resultJso.get("msgEx"));
		JSONObject object = JSONObject.parseObject(resultJson1);
		if(null != resultJso && "0000".equals(resultJso.get("code")) && "-1".equals(object.get("status"))){
			//发送验证码
			String result5 = validateCodeService.sendValidateCode(mobile, type);
			JSONObject resultJso5 = JSONObject.parseObject(result5);
			String resultJson5 = JSONObject.toJSONString(resultJso5.get("msgEx"));
			JSONObject object1 = JSONObject.parseObject(resultJson5);
			if("0".equals(object1.get("status"))){
				String msg = object1.getString("infos");
				JSONObject msg1 = JSONObject.parseObject(msg);
				//成功发送，验证码入库
				validateCodeService.saveCode(mobile, type,msg1.getString("data"));
				return Response.success();
			}else{
				return Response.fail("短信通道异常,验证码发送失败");
			}
		}else{
			return Response.fail("该用户已经注册过了，请直接登入");
		}
	}
	
	
	/**
	 * 忘记密码发送验证码入口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * Response
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月30日
	 */
	@RequestMapping(value = "/sendCode",method= RequestMethod.POST)
	@ResponseBody
	public Response sendCode(HttpServletRequest request, HttpServletResponse response,Model model){
		Response res = Response.success();
		String mobile = request.getParameter("f_mobile");
		String type = request.getParameter("type");
		//判断手机号是否已经存在
		String result = customerService.selectCustomer(mobile);
		JSONObject resultJso = JSONObject.parseObject(result);
		String resultJson1 = JSONObject.toJSONString(resultJso.get("msgEx"));
		JSONObject object = JSONObject.parseObject(resultJson1);
		if(null != resultJso && "0000".equals(resultJso.get("code")) && "0".equals(object.get("status"))){
			//发送验证码
			String result5 = validateCodeService.sendValidateCode(mobile, type);
			JSONObject resultJso5 = JSONObject.parseObject(result5);
			String resultJson5 = JSONObject.toJSONString(resultJso5.get("msgEx"));
			JSONObject object1 = JSONObject.parseObject(resultJson5);
			if("0".equals(object1.get("status"))){
				//成功发送，验证码入库
				String msg = object1.getString("infos");
				JSONObject msg1 = JSONObject.parseObject(msg);
				validateCodeService.saveCode(mobile, type,msg1.getString("data"));
				/*res.setData(resultJson.get("msgEx"));
				return res;*/
			}
		}else{
			res.setMsg("该用户不存在，请先注册");
			return res;
		}
		return res;
		
	}
	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * Response
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月30日
	 */
	@RequestMapping(value = "/forgotPwd",method= RequestMethod.POST)
	@ResponseBody
	public Response forgotPwd(HttpServletRequest request, HttpServletResponse response,Model model){
		Response res = Response.success();
		String phone = request.getParameter("phone");
		String vaildatecode = request.getParameter("vaildatecode");
		String type = request.getParameter("type");
		String password = request.getParameter("password");
		if(StrUtils.isEmpty(phone)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入手机号");
			return res;
		}
		if(StrUtils.isEmpty(vaildatecode)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入验证码");
			return res;
		}
		if(StrUtils.isEmpty(type)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("类型不能空");
			return res;
		}
		if(StrUtils.isEmpty(password)){
			res.setCode(Constants.CODE_FAILURE);
			res.setMsg("请输入密码");
			return res;
		}
		
		//检验验证码是否正确
				String resultString01 = validateCodeService.validateSelect(phone,type, vaildatecode);
				if(StringUtils.isNotBlank(resultString01)){
					JSONObject resultJson = JSONObject.parseObject(resultString01);
					String resultJson1 = JSONObject.toJSONString(resultJson.get("msgEx"));
					JSONObject object = JSONObject.parseObject(resultJson1);
					if(null != resultJson && "0000".equals(resultJson.get("code")) && "0".equals(object.get("status"))){
						//执行重置密码
						String resultString = customerService.forgotPassword(phone,password);
						System.out.println(resultString);
						if(StringUtils.isNotBlank(resultString)){
						      JSONObject resultJson2 = JSONObject.parseObject(resultString);
						      if(null != resultJson2 && resultJson2.get("code").equals("0000")){
									res.setData(resultJson2.get("msgEx"));
									return res;
						      }else{
						    	  res.setCode(Constants.CODE_FAILURE);
						    	  res.setMsg("error");
						    	  return res;
						      }
						    }
					}else{
						res.setData(resultJson.get("msgEx"));
					}
				}
		
		
		return res;
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
/*	
	String getVFCode(){
		Random ran= new Random();	
		String code =(ran.nextInt(9000)+1000)+"";		
		return code;
	}*/
	

	

}
