package com.ymkj.smi.manager.web.api.model.base;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;


@Getter
@Setter
public class Model_001001 extends ReqParam{

	/**
	 * 登录(作业船APP)
	 */
	private static final long serialVersionUID = 6964678828133095263L;
	
	@NotBlank(message="工号不能为空")
    private String jno;

    @NotBlank(message="密码不能为空")
    private String password;

	
}
