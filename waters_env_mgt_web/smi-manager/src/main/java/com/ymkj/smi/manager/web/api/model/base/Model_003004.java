package com.ymkj.smi.manager.web.api.model.base;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_003004 extends ReqParam {

	private static final long serialVersionUID = -8248061996098095057L;
	
	@NotBlank(message="任务编号不能为空")
    private String taskCode;
	
	@NotBlank(message="评价不能为空")
    private String judge;
	
}
