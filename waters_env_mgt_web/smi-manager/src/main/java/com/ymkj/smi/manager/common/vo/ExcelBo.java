package com.ymkj.smi.manager.common.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * @Description：ExcelBo
 * @ClassName: ExcelBo.java
 * @Author：tianx
 * @Date：2017年8月7日
 * -----------------变更历史-----------------
 * 如：who  2017年8月7日  修改xx功能
 */
@Getter
@Setter
public class ExcelBo {
	
	private String title;//标题
	private String fileName;//文件名
	private String subTitle;//子标题
	private Map<String,Object> params;//查询条件
	private String[] thead ;//表头
	private List<Map<Integer,Object>> data;//数据
	
}
