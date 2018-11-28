package com.ymkj.smi.wsapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBean {

	// 开关关闭
	public static final String SWITCH_OFF = "OFF";

	@Value("${url}")
	private String url;

	@Value("${md5.signKey}")
	private String md5SignKey;

	@Value("${ce_validate_switch}")
	private String ceValidateSwitch;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMd5SignKey() {
		return md5SignKey;
	}

	public void setMd5SignKey(String md5SignKey) {
		this.md5SignKey = md5SignKey;
	}

	public String getCeValidateSwitch() {
		return ceValidateSwitch;
	}

	public void setCeValidateSwitch(String ceValidateSwitch) {
		this.ceValidateSwitch = ceValidateSwitch;
	}

}
