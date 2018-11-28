package com.ymkj.smi.manager.common.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;


/**
 * @Description：垃圾统计数据报表视图对象
 * @ClassName: GarbageReportVo.java
 * @Author：tianx
 * @Date：2017年8月3日
 * -----------------变更历史-----------------
 * 如：who  2017年8月3日  修改xx功能
 */
@Getter
@Setter
public class GarbageReportVo {
	
	private String title;//标题
	
	private String subTitle;//子标题
	
	private BigDecimal total;//总收集量
	
	private String ytitle;//y轴维度
	
	private List<String> xaxis;//x轴维度
	
	private List<Map<String,Object>> series;//数据
	
}
