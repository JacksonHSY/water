package com.ymkj.smi.manager.web.admin;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Announcement;
import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.service.AnnouncementService;
import com.ymkj.springside.modules.orm.PageInfo;
import com.ymkj.springside.modules.utils.Response;
import com.ymkj.springside.modules.utils.StrUtils;

@Controller
@RequestMapping("/admin/announcement")
public class AnnouncementController {
	@Autowired
	private AnnouncementService announcementService; 
	
	@RequestMapping("")
	public String index(Model model,HttpSession httpSession) {
		return "announcement/announcementList";
	}
	/**
	 * 
	 * 功能描述：列表显示
	 * 输入参数：
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param announcemnt
	 * @param request
	 * @return
	 * 返回类型：ResultVo
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	@RequestMapping("/announcementList")
	@ResponseBody
	public ResultVo announcementListPage(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "order", defaultValue = "desc") String order,
			Announcement announcemnt,HttpServletRequest request) {
	    PageInfo<Announcement> requestbody = new PageInfo<Announcement>();
	    if(StrUtils.isEmpty(announcemnt.getTitle())){
	    	announcemnt.setTitle(null);
	    }
	    requestbody.setPageNo(page);
		requestbody.setPageSize(rows);
		requestbody.setQueryParam(announcemnt);
		requestbody = announcementService.getAnnouncementListPage(requestbody);
		return ResultVo.returnPage(requestbody);
	}
	/**
	 * 
	 * 功能描述:新建和编辑公告
	 * 输入参数：
	 * @param announcement
	 * @param request
	 * @return
	 * 返回类型：ResultVo
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAnnouncement",method = RequestMethod.POST)
	//@SystemControllerLog(description = "保存",action = com.ymkj.thumb.assistant.manager.constants.Constants.LOGMODULE_字典管理)
	public ResultVo saveAnnouncement(Announcement announcement,HttpServletRequest request) {
		try {
			if(announcement.getId()==null){
				announcement.setCreateTime(DateUtil.getDateFormatDate(new Date()));
				announcement.setStatus(Constants.DATA_VALID);
				announcement.setUpdateTime(DateUtil.getDateFormatDate(new Date()));
				AdminUser user = (AdminUser) request.getSession().getAttribute(Constants.SYS_LOGIN_USR);
				announcement.setIssue(user.getName());
				announcementService.insert(announcement);
			}else{
				Announcement query = new Announcement();
				query.setId(announcement.getId());
				Announcement record = announcementService.selectOne(query);
				if(null == record){
					return  ResultVo.returnMsg(false, "修改失败,公告不存在或已失效!");
				}
				record.setUpdateTime(DateUtil.getDateFormatDate(new Date()));
				record.setContent(announcement.getContent());
				record.setTitle(announcement.getTitle());
				record.setReleaseChannel(announcement.getReleaseChannel());
				record.setIssue("admin");
				announcementService.updateByPrimaryKeySelective(record);
			}
			return  ResultVo.returnMsg(true, "公告创建成功");
		} catch (Exception e) {
			e.printStackTrace();
			return  ResultVo.returnMsg(false, "程序异常，请稍后重试!");
		}
	}
	/**
	 * 
	 * 功能描述：获得公告信息
	 * 输入参数：
	 * @param id
	 * @param request
	 * @return
	 * 返回类型：Response
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo",method = RequestMethod.POST)
	public Response getInfo(Long id,HttpServletRequest request) {
		Response response = Response.success();
		try {
			if(id==null){
				response.setCode(Constants.CODE_FAILURE);
				response.setMsg("查看失败,该记录不存在!");
				return response;
			}else{
				Announcement query = new Announcement();
				query.setId(id);
				Announcement record = announcementService.selectOne(query);
				if(null == record){
					response.setCode(Constants.CODE_FAILURE);
					response.setMsg("查看失败,记录不存在!");
					return response;
				}
				response.setData(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Constants.CODE_FAILURE);
			response.setMsg("程序异常，请稍后重试!");
		}
		return response;
	}
	/**
	 * 
	 * 功能描述：删除公告
	 * 输入参数：
	 * @param id
	 * @param request
	 * @return
	 * 返回类型：ResultVo
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	@ResponseBody
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public ResultVo del(Long id,HttpServletRequest request) {
		try {
				Announcement query = new Announcement();
				query.setId(id);
				Announcement record = announcementService.selectOne(query);
				if(null == record){
					return  ResultVo.returnMsg(false, "删除失败,公告不存在或已失效!");
				}
				record.setStatus(Constants.DATA_UNVALID);
				announcementService.updateByPrimaryKeySelective(record);
				return  ResultVo.returnMsg(false, "公告已删除!");
		} catch (Exception e) {
			e.printStackTrace();
			return  ResultVo.returnMsg(false, "删除失败,系统异常!");
		}
	}
}