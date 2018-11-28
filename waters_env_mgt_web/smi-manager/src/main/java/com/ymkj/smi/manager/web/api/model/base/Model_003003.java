package com.ymkj.smi.manager.web.api.model.base;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_003003 extends ReqParam {

	@NotBlank(message="任务编号不能为空")
    private String taskCode;

	
}
