package com.ymkj.smi.manager.web.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerShipsDto extends BaseDTO{
	
	private static final long serialVersionUID = 5088689818831366099L;

	/**
	 * 客户船的id
	 */
	private Long customerShipId;

	 /**
	  * 客户船名
	  */
	private String customerShipName;

}
