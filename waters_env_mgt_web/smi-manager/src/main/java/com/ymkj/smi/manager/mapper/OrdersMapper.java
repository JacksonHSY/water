package com.ymkj.smi.manager.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.vo.GarbageReportBO;
import com.ymkj.smi.manager.common.vo.WorkShipBo;
import com.ymkj.springside.modules.orm.mybatis.JdMapper;

/**
* OrdersMapper
* <p/>
* Author: 
* Date: 2017-07-24 18:31:59
* Mail: 
*/
public interface OrdersMapper extends JdMapper<Orders, Long> {

	Page<Orders> selectOrderListByConditions(Orders order);
	
	List<Orders> getOrdersForAmountGarbageTop(Map<String, Object> map);
	
	List<Orders> getOrdersForOrdersSumTop(Map<String, Object> map);
	
	List<Orders> getOrdersForHighPraiseTop5();

	public List<Orders> getDailyStatisticPageList(Map<String, Object> map);

	public List<Orders> getMonthStatisticByLiaison(Map<String, Object> map);
	
	public List<Orders> getMonthStatisticByWorkShip(Map<String, Object> map);
	
	public List<Orders> getMonthStatisticByCustomer(Map<String, Object> map);
	
	GarbageReportBO queryGarbageTotalByDate(Map<String, Object> map);

	List<Orders> getWorkShipReportByWeeks(Long id);
	
	List<Orders> getWorkShipReportByDays(Long id);
	public Page<Orders> selectOrders(Map<String, Object> map);
	
	public String selectOrdersCount(Map<String, Object> map);
	
	List<Orders> getJudgeList(Long id);

	public Page<Orders> selectWorkOrders(Map<String, Object> map);
}