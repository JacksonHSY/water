package com.ymkj.smi.manager.common.entity;

import java.util.List;
import java.util.Map;

/**
 * @author     ym10093  
 * @createTime 2016年11月3日 下午8:13:26
 */
public class MenuTreeModelRole implements java.io.Serializable{
	
 	private static final long serialVersionUID = 5246422024276944186L;

	private String id;
	private String text;
	private String iconCls;
	private String url;
	private Boolean leaf;
	private Boolean checked;
	private Boolean expanded;
	private Map<String, Object> attributes;
	private List<MenuTreeModelRole> children;

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<MenuTreeModelRole> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeModelRole> children) {
		this.children = children;
	}

}
