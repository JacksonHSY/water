package com.ymkj.smi.manager.web.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by spark on 16/8/4.
 * 登录信息DTO
 */
@Getter
@Setter
public class CustomerDTO extends BaseDTO{

	private static final long serialVersionUID = -3872168083025075506L;

	private Long id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 姓名
    */
    private String cusName;

    /**
    * 联系电话
    */
    private String phone;

}
