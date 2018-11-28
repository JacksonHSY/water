package com.ymkj.smi.manager.web.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


import com.ymkj.smi.manager.common.entity.Orders;
@Getter
@Setter
public class OrdersDTO extends BaseDTO{

	/**
	 * 信息
	 */
	private static final long serialVersionUID = -6300514389594003612L;
	
	private Long id;
	
	
	private Integer pageNo;
	
	
	private List<Orders> results;

}
