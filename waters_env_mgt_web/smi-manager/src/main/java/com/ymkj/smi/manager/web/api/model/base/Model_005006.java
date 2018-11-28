package com.ymkj.smi.manager.web.api.model.base;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.common.entity.CustomerShip;
import com.ymkj.smi.manager.common.vo.ShipVo;
import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_005006 extends ReqParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2851119553992215718L;
	
    private Long id;
	
	@NotNull(message="联系人ID不能为空")
    private Long cId;
	
	@NotBlank(message="联系人姓名不能为空")
    private String cusName;
	
	private List<ShipVo> shipList;
}
