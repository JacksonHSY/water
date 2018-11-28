package com.ymkj.smi.manager.common.entity;

import java.util.List;
import java.util.Map;

import com.ymkj.springside.modules.utils.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ym10093
 * @createTime 2016年11月3日 下午8:13:26
 */
@Setter
@Getter
public class MenuTreeModel implements java.io.Serializable {

	private static final long serialVersionUID = 5246422024276944186L;

	private String id;
	private String text;
	private String iconCls;
	private String state;
	private Map<String, Object> attributes;
	private List<MenuTreeModel> children;

	@Override
	public String toString() {
		return ToStringBuilder.build(this);
	}
}
