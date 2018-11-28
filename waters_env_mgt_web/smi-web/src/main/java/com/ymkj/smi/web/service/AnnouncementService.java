package com.ymkj.smi.web.service;

import org.springframework.stereotype.Service;

import com.ymkj.smi.web.network.HttpClientUtil;
import com.ymkj.smi.web.network.Param;

import net.sf.json.JSONObject;

@Service
public class AnnouncementService {
	/**
	 * 公告分页
	 * @param id
	 * @return
	 */
	public  String getAnnouncementList(Integer pageNo,Integer pageSize){
		JSONObject model = new JSONObject();
		model.put("pageNo",pageNo);
		model.put("pageSize", pageSize);
		model.put("realseChannel", "作业船app");
		model.put("source", "web");
		String params = Param.getParam("002001", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
	/**
	 * 公告详情
	 * @param id
	 * @return
	 */
	public String getAnnouncementDetail(Long id){
		JSONObject model = new JSONObject();
		model.put("id",id);
		String params = Param.getParam("002002", model);
		String result = HttpClientUtil.sendHttpPost(params);
		return result;
	}
}
