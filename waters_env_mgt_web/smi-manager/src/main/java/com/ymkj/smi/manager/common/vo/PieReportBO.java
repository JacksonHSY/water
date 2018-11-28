package com.ymkj.smi.manager.common.vo;

import java.math.BigDecimal;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description：返回数据对象
 * @ClassName: ResultVo.java
 * @Author：tianx
 * @Date：2017年7月27日
 * -----------------变更历史-----------------
 * 如：who  2017年7月27日  修改xx功能
 */
@Getter
@Setter
public class PieReportBO {
	
	private String name;//废弃物分类名
	
	private String title;//环形标题
	
	private BigDecimal y;//收集量
	
	private BigDecimal momRate;//环比增长率
	
	private BigDecimal yoyRate;//同比增长率
	
	public PieReportBO(String name, BigDecimal y) {
		super();
		this.name = name;
		this.y = y;
	}
	
}
