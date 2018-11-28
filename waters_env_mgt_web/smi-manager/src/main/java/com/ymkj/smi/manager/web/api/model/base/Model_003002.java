package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Getter
@Setter
public class Model_003002 extends ReqParam {

	@NotNull(message="用户id不能为空")
    private Long id;
	
	@NotNull(message="当前页不能为空")
    private int pageNo;
    
    @NotNull(message="显示条数不能为空")
    private int pageSize;
    
}
