package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;
/**
 * 
 * @Description：ren
 * @ClassName: Model_004001.java
 * @Author：wangno01
 * @Date：2017年8月4日
 * -----------------变更历史-----------------
 * 如：who  2017年8月4日  修改xx功能
 */
@Getter
@Setter
public class Model_004003 extends ReqParam {
	private static final long serialVersionUID = 222088649079320322L;

	@NotNull(message="id不能为空")
    private Long id;
}
