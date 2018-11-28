package com.ymkj.smi.manager.web.api.model.base;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model_006004 extends ReqParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388408240732896281L;
	
	@NotNull
	private Long id;

}
