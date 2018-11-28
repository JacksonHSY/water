package com.ymkj.smi.web.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.web.service.AnnouncementService;
import com.ymkj.smi.web.utils.PageNavUtil;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	
	@Autowired
	private AnnouncementService announcementService;
	
	@RequestMapping("/announcementDetail")
	public String getDetail(Model model,@RequestParam(value = "id") Long id) {
		JSONObject resultJson = JSONObject.parseObject(announcementService.getAnnouncementDetail(id));
		model.addAttribute("msg", resultJson.get("msgEx"));
		return "announcement/announcementDetail";
	}
	
	@RequestMapping("/announcementList")
	public String getList(@RequestParam(value = "page", defaultValue = "1") int page,Model model,HttpSession httpSession) {
		String s=announcementService.getAnnouncementList(page,5);
		JSONObject resultJson = JSONObject.parseObject(s);
		if(null != resultJson && resultJson.get("code").equals("0000")){
			model.addAttribute("msg", resultJson.get("msgEx"));
			JSONObject info = JSONObject.parseObject(resultJson.getString("msgEx"));
			JSONObject pageInfo = JSONObject.parseObject(info.getString("infos"));
		    model.addAttribute("currentPage", 	pageInfo.get("pageNo"));
		    model.addAttribute("totalCount",pageInfo.get("totalRecord"));
		    model.addAttribute("totalPage",pageInfo.get("totalPage"));
		    pageInfo.getIntValue("pageSize");
		    model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(page, pageInfo.getIntValue("pageSize"),pageInfo.getIntValue("totalRecord"),pageInfo.getIntValue("pageSize") ));
		}
	    return "announcement/announcementList";
	}
	
}
