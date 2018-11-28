package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;
@Getter
@Setter
public class Model_005004 extends ReqParam{

	/**
	 * 通过手机号查询客户信息
	 */
	private static final long serialVersionUID = 8849931916106549033L;
	@NotNull(message="手机号不能为空")
    private String phone;

}
