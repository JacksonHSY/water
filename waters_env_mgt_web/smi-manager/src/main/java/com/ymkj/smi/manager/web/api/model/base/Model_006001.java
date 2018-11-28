package com.ymkj.smi.manager.web.api.model.base;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_006001 extends ReqParam{

	/**
	 * 保存验证码
	 */
	private static final long serialVersionUID = 5197272695656972564L;
	@NotBlank(message="手机号不能为空")
	private String mobile;
	
	@NotBlank(message="验证码不能为空")
	private String validateCode;
	
	/*@NotBlank(message="客户id不能为空")
	private Long customerId;*/
	
	@NotBlank
	private String type;

}
