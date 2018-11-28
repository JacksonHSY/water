package com.ymkj.smi.manager.service.task;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymkj.smi.manager.common.constants.Constants;
import com.ymkj.smi.manager.common.entity.Orders;
import com.ymkj.smi.manager.common.untils.DateUtil;
import com.ymkj.smi.manager.mapper.OrdersMapper;
import com.ymkj.smi.manager.service.OrdersService;
/**
 * 更新七日前任务评价
 * @author YM10208
 *
 */
@Slf4j
@Service
public class UpdateJudgeForOrderTask {
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrdersMapper ordersMapper ;
	public void executor(){
		log.info("自动评价任务开始执行");
		Date date = new Date();
		try {
			Orders order = new Orders();
			order.setTaskStatus(Constants.DEAL_DONE);
			order.setUpdateTime(DateUtil.addDays(date, -7));
			 List<Orders> list = ordersService.shipOrders(order,"job");
	    	for(Orders ls : list){
	    		ls.setJudge("5");
	    		ls.setTaskStatus(Constants.EVAL_DONE);
	    		ls.setUpdateTime(date);
	    		ls.setJudgeTime(date);
	    		ordersMapper.updateByPrimaryKeySelective(ls);
	    	}
			log.info("自动评价任务执行条数=="+list.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("自动更新评价异常", e);
		}
		log.info("自动评价任务执行结束");
	}
}
