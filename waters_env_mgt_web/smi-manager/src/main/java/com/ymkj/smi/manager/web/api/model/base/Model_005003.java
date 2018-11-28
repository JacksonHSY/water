package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;
@Getter
@Setter
public class Model_005003 extends ReqParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1047203830227440864L;
	@NotNull(message="手机号不能为空")
    private String phone;
	
	@NotNull(message="密码不能为空")
    private String password;
	
	private String sNature;
}
