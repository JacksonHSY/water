package com.ymkj.smi.manager.web.api.model.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.ymkj.smi.manager.common.untils.JSONUtils;
import com.ymkj.smi.manager.web.api.model.base.Model_001002;
import com.ymkj.smi.manager.web.api.model.base.Model_002001;
import com.ymkj.smi.manager.web.api.model.base.Model_002002;
import com.ymkj.smi.manager.web.api.model.base.Model_003001;
import com.ymkj.smi.manager.web.api.model.base.Model_003002;
import com.ymkj.smi.manager.web.api.model.base.Model_003003;
import com.ymkj.smi.manager.web.api.model.base.Model_003004;
import com.ymkj.smi.manager.web.api.model.base.Model_003005;
import com.ymkj.smi.manager.web.api.model.base.Model_004001;
import com.ymkj.smi.manager.web.api.validate.ReqHeadValidate;
import com.ymkj.smi.manager.web.api.validate.ReqParamValidate;

public class ReqMain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	@ReqParamValidate
    private ReqParam reqParam;

	@ReqHeadValidate
    private ReqHeadParam reqHeadParam;

    @NotBlank(message="sn不能为空")
    private String sn;

    private String reqUrl;

    private String projectNo;

    @NotBlank(message="reqTimestamp不能为空")
    private String reqTimestamp;

    public static void main(String[] args) {
//    	ReqMain rm = new ReqMain();
//    	Model_001002 model = new Model_001002();
//    	model.setLoginName("admin");
//    	model.setPassword("123");
//    	rm.setReqParam(model);
//    	rm.setReqHeadParam(new ReqHeadParam());
//    	
//    	rm.setSn("11111");
//    	rm.setReqTimestamp("2017-08-04");
//    	System.out.println(JSONUtils.toJSON(rm));
    	ReqMain rm = new ReqMain();
    	Model_003005 model = new Model_003005();
    	model.setTaskCode("2017072716362000000");
//    	model.setJudge("tset");
    	model.setTaskStatus("0");
    	model.setAdress("tset1");
    	model.setTaskType("1");
    	model.setIfType("1");
    	model.setWorkDate(new Date());
    	rm.setReqParam(model);
    	rm.setReqHeadParam(new ReqHeadParam());
    	
    	rm.setSn("11111");
    	rm.setReqTimestamp("2017-08-08");
    	System.out.println(JSONUtils.toJSON(rm));
	}

    public ReqHeadParam getReqHeadParam() {
        return reqHeadParam;
    }

    public void setReqHeadParam(ReqHeadParam reqHeadParam) {
        this.reqHeadParam = reqHeadParam;
    }

    public ReqParam getReqParam() {
        return reqParam;
    }

    public void setReqParam(ReqParam reqParam) {
        this.reqParam = reqParam;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getReqTimestamp() {
        return reqTimestamp;
    }

    public void setReqTimestamp(String reqTimestamp) {
        this.reqTimestamp = reqTimestamp;
    }
}