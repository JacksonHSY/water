package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.web.api.dto.CustomerShipsDto;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* CustomerShipMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:49
* Mail: 
*/
public interface CustomerShipMapper extends JdMapper<CustomerShip, Long> {
	/**
	 * 查询客户船信息
	 * @param map
	 * @return
	 * List<CustomerShip>
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<CustomerShip> getCustomerShipList(Map<String, Object> map);
	/**
	 * 插入客户船信息
	 * @param record
	 * @return
	 * int
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public int insertCustomerShip(CustomerShip record);
	
	public List<CustomerShipsDto> getShipsByCusId(Map<String, Object> map);

}