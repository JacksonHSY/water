package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;
@Getter
@Setter
public class Model_004002 extends ReqParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6546877983797115987L;
	@NotBlank(message="difference不能为空")
	private String difference;
	
	@NotBlank(message="用户名不能为空")
	private String name;
	
	@NotNull(message="当前页数不能为空")
	private int pageNo;
	
	@NotNull(message="显示条数不能为空")
	private int pageSize;

}
