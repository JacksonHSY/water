package com.ymkj.smi.manager.web.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkShipsDto extends BaseDTO{
	
	private static final long serialVersionUID = -499601289485239928L;

	/**
     * 作业船的id
     */
 	private Long workShipId;

     /**
     * 作业船名
     */
     private String ShipName;

}
