package com.ymkj.smi.manager.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Announcement;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.common.untils.PageUtils;
import com.ymkj.smi.manager.mapper.AnnouncementMapper;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_002001;
import com.ymkj.smi.manager.web.api.model.base.Model_002002;
import com.ymkj.springside.modules.orm.PageInfo;

/**
* AnnouncementService
* <p/>
* Author: 
* Date: 2017-07-24 18:31:43
* Mail: 
*/
@Service
public class AnnouncementService  {
	@Autowired
	private AnnouncementMapper announcementMapper;
	
	/*@SuppressWarnings({ "unchecked", "rawtypes" })*/
/*	public PageInfo<Announcement> getAnnouncementListPage(PageInfo<Announcement> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Announcement announcement = (Announcement) pageInfo.getQueryParam();
		announcement.setStatus(Constants.DATA_VALID);
		Page<Announcement> page = (Page)announcementMapper.selectOrderListByConditions(announcement);
        return PageUtils.convertPage(page);
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<Announcement> getAnnouncementListPage(PageInfo<Announcement> pageInfo) {
		PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize());
		Announcement announcement = (Announcement) pageInfo.getQueryParam();
		announcement.setStatus(Constants.DATA_VALID);
		announcement.setCreateTimeStr(DateUtil.format(announcement.getCreateTime(), DateUtil.DATAFORMAT_YYYY_MM_DD));
		Page<Announcement> page = (Page)announcementMapper.selectAnnouncementListByConditions(announcement);
        return PageUtils.convertPage(page);
	}
	/**
	 * 
	 * 功能描述：保存公告信息
	 * 输入参数：
	 * @param announcement
	 * 返回类型：void
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	public void insert(Announcement announcement){
		announcementMapper.insert(announcement);
	}
	/**
	 * 
	 * 功能描述：根据条件查询公告信息
	 * 输入参数：
	 * @param announcement
	 * @return
	 * 返回类型：List<Announcement>
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 *//*
	public List<Announcement> query(Announcement announcement) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(announcement.getTitle())){
			map.put("title",announcement.getTitle()); 
		}
		if(StringUtils.isNotBlank(announcement.getCreateTime().toString())){
			map.put("createTime",announcement.getCreateTime()); 
		}
		if(StringUtils.isNotBlank(announcement.getReleaseChannel())){
			map.put("releaseChannel",announcement.getReleaseChannel()); 
		};
		return announcementMapper.getAnnouncementList(map);
		
	}*/
	/**
	 * 
	 * 功能描述：根据条件查询集合
	 * 输入参数：
	 * @param announ
	 * @return
	 * 返回类型：List<Announcement>
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	public List<Announcement> queryAnnouncement(Announcement announ) {
		Example example = new Example(Announcement.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(announ);
		return announcementMapper.selectByExample(example);
	}
	/**
	 * 
	 * 功能描述：获得公告
	 * 输入参数：
	 * @param id
	 * @return
	 * 返回类型：Announcement
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	public Announcement getAnnouncement(String id){
		Announcement announcement =null;
		Example example = new Example(Announcement.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		List<Announcement> list = announcementMapper.selectByExample(example);
		if(list.size()>0){
			announcement = list.get(0);
			return announcement;
		}
		return null;
	}
	/**
	 * 
	 * 功能描述：更新
	 * 输入参数：
	 * @param announ
	 * @return
	 * 返回类型：int
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	public int updateByPrimaryKeySelective(Announcement announ){
		return announcementMapper.updateByPrimaryKeySelective(announ);
	}
	
	/**
	 * 
	 * 功能描述：查询一条信息
	 * 输入参数：
	 * @param announ
	 * @return
	 * 返回类型：Announcement
	 * 创建人：wangn01
	 * 日期：2017年8月2日
	 */
	public Announcement selectOne(Announcement announ){
		announ.setStatus(Constants.DATA_VALID);
		return announcementMapper.selectOne(announ);
	}
	/**
	 * 
	 * @API公告分页
	 * @param model
	 * @return
	 * Result
	 * @date2017年8月8日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public Result getAnnouncementListPage(Model_002001 model){
		PageHelper.startPage(model.getPageNo(), model.getPageSize());
		Announcement announcement = new Announcement();
		announcement.setStatus(Constants.DATA_VALID);
		announcement.setReleaseChannel(model.getRealseChannel());
		List<Announcement> list  = new ArrayList<Announcement>();
		if("web".equals(model.getSource())){
			list = announcementMapper.selectAnnouncementListByConditionsForWeb(announcement);
		}else{
			 list = announcementMapper.selectAnnouncementListByConditions(announcement);
				
		}
		for(Announcement ann:list){
			ann.setCreateTimeStr(DateUtil.format(ann.getCreateTime(),DateUtil.DATAFORMAT_YYYY_MM_DD ));
		}
		Page<Announcement> page = (Page)list;
		return Result.success(PageUtils.convertPage(page));
	}
	/**
	 * 公告详情
	 * @param model
	 * @return
	 */
	public Result getAnnouncementById(Model_002002 model){
		Announcement announcement = new Announcement();
		announcement.setStatus(Constants.DATA_VALID);
		announcement.setId(model.getId());
		announcement = announcementMapper.selectOne(announcement);
		announcement.setCreateTimeStr(DateUtil.format(announcement.getCreateTime(),DateUtil.DATAFORMAT_YYYY_MM_DD));
		return Result.success(announcement);
	}
}