package com.ymkj.smi.manager.web.api.model.base;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_006002 extends ReqParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228942480117067750L;
	
	@NotBlank(message="手机号不能为空")
	private String mobile;
	
	@NotBlank(message="验证码不能为空")
	private String vaildateCode;
	
	@NotBlank(message="类型不能为空")
	private String type;

}
