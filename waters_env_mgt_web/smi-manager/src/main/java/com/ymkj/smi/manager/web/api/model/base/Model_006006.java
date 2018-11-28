package com.ymkj.smi.manager.web.api.model.base;


import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_006006 extends ReqParam{

	/**
	 * 发送验证码
	 */
	private static final long serialVersionUID = 5197272695656972564L;
	@NotBlank(message="手机号不能为空")
	private String mobile;
	
	@NotBlank(message="短信类型不能为空")
	private String type;
}
