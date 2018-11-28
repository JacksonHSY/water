package com.ymkj.smi.manager.common.vo;

import java.util.List;

import com.ymkj.smi.manager.common.entity.Orders;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @Description：对类进行描述
 * @ClassName: WorkShipBo.java
 * @Author：wangno01
 * @Date：2017年8月4日
 * -----------------变更历史-----------------
 * 如：who  2017年8月4日  修改xx功能
 */
@Getter
@Setter
public class WorkShipReportBo {
	
	private Integer times;
	
	private Double amountGarbage;
	private Double amountGarbageForShow;
	private Double life; //生活垃圾量
	
	private Double burnCinder; //炉灰量
	
	private Double sweeping;//扫舱垃圾
	
	private Double water;//生活污水
	
	private Double foods;//食品废弃物
	
	private Double plastic;//塑料
	
	private Double gungo;//油污
	
	private List<Orders> judgeList;
	
	private List<Orders> orderList;
}
