package com.ymkj.smi.manager.common.vo;

import java.math.BigDecimal;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description：返回视图对象
 * @ClassName: ResultVo.java
 * @Author：tianx
 * @Date：2017年7月27日
 * -----------------变更历史-----------------
 * 如：who  2017年7月27日  修改xx功能
 */
@Getter
@Setter
public class GarbageReportBO {
	
	private BigDecimal garbageTotal;//生活垃圾统计量
	
	private BigDecimal waterTotal;//生活污水统计量
	
	private BigDecimal foodTotal;//食品废弃物统计量
	
	private BigDecimal plasticTotal;//塑料统计量
	
	private BigDecimal sweepingTotal;//扫舱垃圾统计量
	
	private BigDecimal burnCinderTotal;//焚烧炉火渣统计量
	
	private BigDecimal gungoTotal;//油污统计量
	
	@Transient
	private BigDecimal momRate;//环比增长率
	
	@Transient
	private BigDecimal yoyRate;//同比增长率
}
