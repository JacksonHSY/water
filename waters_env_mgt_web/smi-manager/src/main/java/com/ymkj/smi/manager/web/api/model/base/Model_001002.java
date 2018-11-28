package com.ymkj.smi.manager.web.api.model.base;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.web.api.model.req.ReqParam;

/**
 * 登录（网站、便民服务APP）
 */
@Getter
@Setter
public class Model_001002 extends ReqParam {

	private static final long serialVersionUID = 222088649079320322L;

	@NotBlank(message="请输入手机号码")
    private String loginName;

    @NotBlank(message="密码不能为空")
    private String password;
}
