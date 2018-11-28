package com.ymkj.smi.manager.web.api.model.base;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_006003 extends ReqParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7950887610745848137L;
	@NotBlank(message="手机号不能为空")
	private String mobile;
	
	@NotBlank(message="密码不能为空")
	private String password;

}
