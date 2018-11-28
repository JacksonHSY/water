package com.ymkj.smi.manager.web.api.model.base;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_003006 extends ReqParam{
	
	private static final long serialVersionUID = 1964503277956942992L;

	@NotBlank(message="任务编号不能为空")
    private String taskCode;
	
	@NotBlank(message="用户类型不能为空")
    private String userType;

	private String reason;
}
