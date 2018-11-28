package com.ymkj.smi.manager.web.api.model.base;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_001003 extends ReqParam{

	/**
	 * 作业船修改密码
	 */
	private static final long serialVersionUID = -8285803862889364106L;
	
	@NotBlank(message="登入名不能为空")
	private String jno;
	
	@NotBlank(message="初始密码不能为空")
	private String oldPassword;
	
	@NotBlank(message="新密码不能为空")
	private String newPassword;

}
