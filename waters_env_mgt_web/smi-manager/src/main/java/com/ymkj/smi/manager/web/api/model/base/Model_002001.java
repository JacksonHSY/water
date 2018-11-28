package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


import com.ymkj.smi.manager.web.api.model.req.ReqParam;

/**
 * 
 * @Description：查询公告分页
 * @ClassName: Model_002001.java
 * @Author：wangno01
 * @Date：2017年8月4日
 * -----------------变更历史-----------------
 * 如：who  2017年8月4日  修改xx功能
 */
@Getter
@Setter
public class Model_002001 extends ReqParam {

	private static final long serialVersionUID = 222088649079320322L;

	@NotNull(message="当前页数不能为空")
    private Integer pageNo;

	@NotNull(message="显示条数不能为空")
    private Integer pageSize;
	
	private String realseChannel; 
	
	private String source;
}
