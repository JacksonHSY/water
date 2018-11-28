package com.ymkj.smi.manager.web.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCodeDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192488023637376909L;
	
	/**
	 * 验证码
	 */
	private String code;

}
