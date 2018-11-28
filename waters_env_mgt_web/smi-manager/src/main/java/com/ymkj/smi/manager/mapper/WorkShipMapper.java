package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.WorkShip;
import com.ymkj.smi.manager.common.vo.WorkShipBo;
import com.ymkj.smi.manager.web.api.dto.ShipsDto;
import com.ymkj.smi.manager.web.api.dto.WorkShipsDto;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* WorkShipMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:32:10
* Mail: 
*/
public interface WorkShipMapper extends JdMapper<WorkShip, Long> {
	
	List<WorkShip> getWorkShipForOptionCheck();
	
	List<WorkShip> getCaptainGroup();
	
	public List<WorkShipsDto> getShips(Map<String, Object> map);
}