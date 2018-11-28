package com.ymkj.smi.web.vo;

import java.util.List;

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
public class CustomerShipVo {
	
	private String password;
	
	private String cusName;
	
	private String ship;
	
	private List<ShipVo> shipList;
	
}
