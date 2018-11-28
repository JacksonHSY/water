package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Announcement;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* AnnouncementMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:43
* Mail: 
*/
public interface AnnouncementMapper extends JdMapper<Announcement, Long> {
	Page<Announcement> selectAnnouncementListByConditions(Announcement announcement);
	Page<Announcement> selectAnnouncementListByConditionsForWeb(Announcement announcement);
}