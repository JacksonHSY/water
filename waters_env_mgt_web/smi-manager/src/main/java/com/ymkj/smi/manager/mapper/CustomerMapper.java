package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.ymkj.smi.manager.common.entity.Customer;
import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* CustomerMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:45
* Mail: 
*/
public interface CustomerMapper extends JdMapper<Customer, Long> {
	
	/**
	 * 新增客户	
	 * @param record
	 * @return
	 * int
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public int insertCustomer(Customer record);
	
	/**
	 * 查询客户信息
	 * @param map
	 * @return
	 * List<CustomerShip>
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月2日
	 */
	public List<Customer> getCustomerShipList(Map<String, Object> map);
}