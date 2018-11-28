package com.ymkj.smi.manager.web.api.model.base;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_005001 extends ReqParam{

	
	private static final long serialVersionUID = -7376303681036382269L;
	
	@NotBlank(message="字典类型不能为空")
    private String dataType;
	
	
}
