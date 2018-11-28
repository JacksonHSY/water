package com.ymkj.smi.manager.service.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.smi.manager.service.DictionaryService;


public class DictionaryOptionsTag extends TagSupport {
	private String type;
	private String selectedValue;
	private String status;

	private static final long serialVersionUID = 7849807473932337047L;

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		Map<Object, Object> condition = new HashMap<Object, Object>();
		condition.put("dataType", type);
		condition.put("status",status);
		List<Dictionary> dictionaries = DictionaryService.findDictionaryByCondition(condition);
		try {
			StringBuffer results = new StringBuffer("");
			for (Dictionary dictionary : dictionaries) {
				String value = dictionary.getDataValue();
				if(selectedValue != null){
					if(value.equals(selectedValue) || selectedValue.indexOf(value)!=-1){
						results.append("<option value=\"" + value
								+ "\" selected=\"selected\">" + dictionary.getDataName() + "</option>");
					}else{
						results.append("<option value=\"" + value
								+"\">"+ dictionary.getDataName() + "</option>");
					}
				}else{
					results.append("<option value=\"" + value +"\">"+ dictionary.getDataName() + "</option>");
				}
				
			}
			pageContext.getOut().write(results.toString());
		} catch (IOException ex) {
			throw new JspTagException("错误");
		}
		return EVAL_PAGE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	
}
