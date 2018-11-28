package com.ymkj.smi.manager.web.api.model.base;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;




import com.ymkj.smi.manager.web.api.model.req.ReqParam;

/**
 * 公告详情
 * @Description：对类进行描述
 * @ClassName: Model_002002.java
 * @Author：wangno01
 * @Date：2017年8月4日
 * -----------------变更历史-----------------
 * 如：who  2017年8月4日  修改xx功能
 */
@Getter
@Setter
public class Model_002002 extends ReqParam {
	private static final long serialVersionUID = 222088649079320322L;
	
	@NotNull(message="id不能为空")
    private Long id;
	
}
