package com.ymkj.smi.web.config;

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

	@Value("${SMSUrl}")
	private String smsUrl;

	@Value("${channelCode}")
	private String channelCode;

	@Value("${securityKey}")
	private String secKey;
	
	@Value("${valicode_max_try_time}")
	private String valicodeMaxTryTime;
	
	@Value("${sms_switch}")
	private String smsSwitch;
	
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

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getSecKey() {
		return secKey;
	}

	public void setSecKey(String secKey) {
		this.secKey = secKey;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getValicodeMaxTryTime() {
		return valicodeMaxTryTime;
	}

	public void setValicodeMaxTryTime(String valicodeMaxTryTime) {
		this.valicodeMaxTryTime = valicodeMaxTryTime;
	}

	public String getSmsSwitch() {
		return smsSwitch;
	}

	public void setSmsSwitch(String smsSwitch) {
		this.smsSwitch = smsSwitch;
	}
	
}
