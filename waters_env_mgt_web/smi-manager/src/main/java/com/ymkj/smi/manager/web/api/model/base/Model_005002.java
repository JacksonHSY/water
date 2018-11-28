package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_005002 extends ReqParam{

	
	private static final long serialVersionUID = -7964200178697007976L;

	@NotNull(message="用户id不能为空")
    private Long id;
	
	@NotBlank(message="用户类型不能为空")
    private String userType;
	
}
