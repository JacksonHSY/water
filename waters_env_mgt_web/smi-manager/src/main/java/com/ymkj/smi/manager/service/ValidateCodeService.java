package com.ymkj.smi.manager.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.ymkj.smi.manager.common.entity.ValidateCode;
import com.ymkj.smi.manager.common.ex.BusinessException;
import com.ymkj.smi.manager.common.untils.HttpClientUtil;
import com.ymkj.smi.manager.common.untils.MD5;
import com.ymkj.smi.manager.common.vo.ResultVo;
import com.ymkj.smi.manager.config.ApplicationBean;
import com.ymkj.smi.manager.mapper.ValidateCodeMapper;
import com.ymkj.smi.manager.web.api.dto.ValidateCodeDTO;
import com.ymkj.smi.manager.web.api.model.Result;
import com.ymkj.smi.manager.web.api.model.base.Model_006001;
import com.ymkj.smi.manager.web.api.model.base.Model_006002;
import com.ymkj.springside.modules.utils.StrUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ValidateCodeService
* <p/>
* Author: changj
* Date: 2017-08-28 09:39:41
* Mail: 
*/
@Slf4j
@Service
public class ValidateCodeService extends BaseService<ValidateCode, Long> {
	
	@Autowired
	private ValidateCodeMapper validateCodeMapper ;
	
	@Autowired
	private ApplicationBean applicationBean;
	
	private ValidateCodeMapper getValidateCodeMapper() {
        return (ValidateCodeMapper) baseMapper;
    }
	
	public ValidateCode getByCodeByMobileAndType(String mobile, String type) {
        ValidateCode validateCode = new ValidateCode();
        validateCode.setMobile(mobile);
        validateCode.setType(type);
        return getValidateCodeMapper().selectOne(validateCode);
    }
	
	public void checkValidateCode(String type, String mobile, String validateCode) {
        ValidateCode customerValidateCode = getByCodeByMobileAndType(mobile, type);
        if (customerValidateCode == null) {
            throw new BusinessException("validateCode.not.get");
        }
        if (StringUtils.isEmpty(customerValidateCode.getValidateCode())) {
            throw new BusinessException("validateCode.not.get");
        }
        //判断重试次数，默认5次
        /*if (customerValidateCode.getTryTime() >= Integer.parseInt(applicationBean.getValicodeMaxTryTime())) {
            throw new BusinessException("validateCode.input.maxLimit", new Object[] {applicationBean.getValicodeMaxTryTime()});
        }*/
        customerValidateCode.setTryTime(customerValidateCode.getTryTime() + 1);
        if (getValidateCodeMapper().updateByPrimaryKey(customerValidateCode) <= 0){
            throw new BusinessException("validateCode.update.failed");
        }
        //判断是否超时
        if (!checkValidaterCode(customerValidateCode)) {
            throw new BusinessException("validateCode.invalid");
        }
        if (!customerValidateCode.getValidateCode().equals(validateCode)) {
            throw new BusinessException("validateCode.error");
        }
    }
	
	public void blockValidateCode(String type,String mobile,String validateCode){
        ValidateCode customerValidateCode = getByCodeByMobileAndType(mobile, type);
        String code = RandomStringUtils.randomNumeric(5);//修改短信验证码为5位,以达到使验证码失效的情况
        customerValidateCode.setValidateCode(code);
        if (getValidateCodeMapper().updateByPrimaryKey(customerValidateCode) <= 0){
            throw new BusinessException("validateCode.update.failed");
        }

    }
	
	/*public void sendValidateCode(String type, String mobile, String busiName) throws Exception {

        String code = RandomStringUtils.randomNumeric(4);
        //log.info("验证码: {}", code);
        ValidateCode customerValidateCode = getByCodeByMobileAndType(mobile, type);
        if (customerValidateCode != null) {
            customerValidateCode.setValidateCode(code);
            customerValidateCode.setCreateTime(new Date());
            customerValidateCode.setTryTime(0);
            if (getValidateCodeMapper().updateByPrimaryKey(customerValidateCode) <= 0) {
                //log.error("更新短信发送次数失败！mobile=" + mobile);
                throw new BusinessException("validateCode.update.failed");
            }
        } else {
            ValidateCode entity = new ValidateCode();
            entity.setValidateCode(code);
            entity.setCreateTime(new Date());
            entity.setMobile(mobile);
            entity.setType(type);
            entity.setTryTime(0);
            if (getValidateCodeMapper().insert(entity) <= 0) {
                //log.error("插入发送短信信息失败！mobile=" + mobile);
                throw new BusinessException("validateCode.send.failed");
            }
        }


        String msgContent = busiName+"，验证码"+code+"，有效期为1分钟,感谢您使用。";
        //tpp短信发送
        String resultJson = sendMsg(mobile,msgContent);

        //900000成功
        JSONObject data = JSONObject.parseObject(resultJson);
        String ucCode = data.getString("ucCode");
//			String ucMessage = data.getString("ucMessage");

        //写入数据库
        MessageLog msgLog = new MessageLog();
        msgLog.setMeContent(msgContent);
        msgLog.setMeTelNum(mobile);
        msgLog.setMeResponse(resultJson);
        msgLog.setMePlatform("0");
        msgLog.setMeSendTime(new Date());
        messageLogService.save(msgLog);;

        if("900000".equals(ucCode)){
            log.info("发送短信验证码成功： {}", resultJson);
        }else{
            log.error("发送短信验证码失败： {}", resultJson);
            throw new BusinessException("validateCode.send.failed");
        }

    }*/
	
	

    public boolean checkValidaterCode(
            ValidateCode customerValidateCode) {
        if (customerValidateCode == null||customerValidateCode.getCreateTime() == null)
            return false;
        Date codeEndTime = new Date();
        Date codeStartTime = customerValidateCode.getCreateTime();
        if (codeEndTime.getTime() - codeStartTime.getTime() <= 1 * 60 * 1000) { // 验证码在1分钟内的
            // 则判为有效
            return true;
        }
        return false;
    }
    /**
	 * 发送短信
	 * 
	 * @param mobile 手机号
	 * @param content 内容
	 */
	public Result sendValidateCode(String mobile, String type){
		String validateCode = RandomStringUtils.randomNumeric(4);
		String content = "";
		if("0".equals(type)){
			content = "注册验证码为："+validateCode+"感谢您的使用";
		}else{
			 content = "重置密码的验证码为："+validateCode+"感谢您的使用";
		}
		try {
			StringBuilder params = new StringBuilder();
			params.append("channelCode=").append(applicationBean.getChannelCode()).append("&mobile=").append(mobile);
	
	    	//按UTF-8进行URL编码,参数串首、尾部加上通讯密钥进行加密，生成签名。
	    	String sign = MD5.MD5Encode(applicationBean.getSecKey() + URLEncoder.encode(params.toString(), "utf-8") + applicationBean.getSecKey());
	    	params.append("&content=").append(URLEncoder.encode(URLEncoder.encode(content,"utf-8"), "utf-8")).append("&sign=").append(sign);
	    	
	    	log.info(MessageFormat.format("手机号：{0};短信发送入参:{1}", mobile, params.toString()));
	    	String response = HttpClientUtil.sendHttpGet(applicationBean.getSmsUrl(), params.toString());
	    	log.info(MessageFormat.format("手机号：{0};短信发送结果:{1}", mobile, response));
	    	if(StrUtils.isEmpty(response)){
	    		return Result.fail("发送失败,短信通道连接超时");
	    	}else{
	    		JSONObject json = JSONObject.parseObject(response);
		    	String code = json.getString("code");
		    	if(!"1".equals(code)){
		    		return Result.fail("短信发送失败");
		    	}
		    	ResultVo rv = new ResultVo();
		    	rv.setData(validateCode);
		    	return Result.success(rv);
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Result.fail("短信发送失败");
		}catch (Exception e) {
			e.printStackTrace();
			return Result.fail("发送失败,短信通道连接超时");
		}
	}
    /**
     * 保存验证码
     * @param model
     * @return
     * Result
     * @author huangsy@yuminsoft.com
     * @date  2017年8月29日
     */
	public Result saveCode(Model_006001 model) {
		// TODO Auto-generated method stub
		String mobile = model.getMobile();
		//Long customerId = model.getCustomerId();
		String type = model.getType();
		//String validateCode = model.getValidateCode();
		//String code = RandomStringUtils.randomNumeric(4);
		 ValidateCode customerValidateCode = getByCodeByMobileAndType(mobile, type);
	        if (customerValidateCode != null) {
	            customerValidateCode.setValidateCode(model.getValidateCode());
	            customerValidateCode.setCreateTime(new Date());
	            customerValidateCode.setTryTime(0);
	            if (getValidateCodeMapper().updateByPrimaryKey(customerValidateCode) <= 0) {
	            	return Result.fail("更新失败");
	            }
	        } else {
	            ValidateCode entity = new ValidateCode();
	            entity.setValidateCode(model.getValidateCode());
	            entity.setCreateTime(new Date());
	            entity.setMobile(mobile);
	            entity.setType(type);
	            entity.setTryTime(0);
	            if (getValidateCodeMapper().insert(entity) <= 0) {
	            	return Result.fail("新增失败");
	            }
	        }
		return Result.success();
	}

	/**
	 * 查询验证码
	 * @param model
	 * @return
	 * Result
	 * @author huangsy@yuminsoft.com
	 * @date  2017年8月29日
	 */
	public Result validateSelect(Model_006002 model) {
		// TODO Auto-generated method stub
		String mobile = model.getMobile();
		String type = model.getType();
		String code = model.getVaildateCode();
		//查询code
		ValidateCode validateCode = new ValidateCode();
        validateCode.setMobile(mobile);
        validateCode.setType(type);
        validateCode = getValidateCodeMapper().selectOne(validateCode);
        //String code1 = validateCode.getValidateCode();
        //checkValidateCode(mobile,type,code);
        
       // ValidateCode customerValidateCode = getByCodeByMobileAndType(mobile, type);
        if (validateCode == null) {
            //throw new BusinessException("validateCode.not.get");
            return Result.fail("查询失败");
        }
        if (StringUtils.isEmpty(validateCode.getValidateCode())) {
        	 return Result.fail("查询失败");
        }
        //判断重试次数，默认5次
        /*if (validateCode.getTryTime() >= Integer.parseInt(applicationBean.getValicodeMaxTryTime())) {
            throw new BusinessException("validateCode.input.maxLimit", new Object[] {applicationBean.getValicodeMaxTryTime()});
        }*/
        if (validateCode.getTryTime() >= 5) {
        	return Result.fail("尝试次数达到上限,请联系客服");
        }
        validateCode.setTryTime(validateCode.getTryTime() + 1);
        if (getValidateCodeMapper().updateByPrimaryKey(validateCode) <= 0){
        	 return Result.fail("更新验证码失败");
        }
        //判断是否超时
        if (!checkValidaterCode(validateCode)) {
        	 return Result.fail("验证码过期，验证失败");
        }
        if (!validateCode.getValidateCode().equals(code)) {
        	 return Result.fail("验证码错误");
        }
		return Result.success("成功");
	}

}