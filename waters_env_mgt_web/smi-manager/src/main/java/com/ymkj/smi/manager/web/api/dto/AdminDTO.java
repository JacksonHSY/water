package com.ymkj.smi.manager.web.api.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminDTO extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1519220291990009560L;

	private Long id;
	
	//登入名
	private String name;
	//工号
	private String jno;
	//手机号
	private String phone;

}
