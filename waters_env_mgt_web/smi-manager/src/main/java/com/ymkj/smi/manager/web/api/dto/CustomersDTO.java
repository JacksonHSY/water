package com.ymkj.smi.manager.web.api.dto;

import java.util.List;

import com.ymkj.smi.manager.common.entity.CustomerShip;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomersDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3088322406265967174L;
	private Long id;

    /**
    * 姓名
    */
    private String userName;

    /**
    * 姓名
    */
    private String cusName;

    /**
    * 联系电话
    */
    private String phone;
    
    private List<CustomerShip> shipList;
    
    private String address;
    
}
