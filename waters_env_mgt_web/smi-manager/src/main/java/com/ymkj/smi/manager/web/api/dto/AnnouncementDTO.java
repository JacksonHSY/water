package com.ymkj.smi.manager.web.api.dto;

import java.util.List;

import com.ymkj.smi.manager.common.entity.Announcement;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by spark on 16/8/4.
 * 登录信息DTO
 */
@Getter
@Setter
public class AnnouncementDTO extends BaseDTO{

	private static final long serialVersionUID = -3872168083025075506L;

	private Long id;
	
	private Integer pageNo;

	private List<Announcement> results;
}
