package com.ymkj.smi.manager.web.api.model.base;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_001004 extends  ReqParam	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641007194509635523L;
	
	/**
	 * 便民服务APP修改密码
	 * 
	 */
	@NotBlank(message="登入名不能为空")
	private String userName;
	
	@NotBlank(message="原密码")
	private String oldPassword;
	
	@NotBlank(message="新密码")
	private String newPassword;
	

}
