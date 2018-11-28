package com.ymkj.smi.manager.web.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipsDto extends BaseDTO{
	

	private static final long serialVersionUID = -2707911454554281323L;

	/**
    * 客户船List
    */
	private List<CustomerShipsDto> customerShipList;

    /**
    * 作业船List
    */
    private List<WorkShipsDto> workShipList;
    

}
