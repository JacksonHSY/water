package com.ymkj.smi.manager.web.api.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.ymkj.smi.manager.common.entity.Dictionary;
import com.ymkj.smi.manager.common.ex.BussErrorCode;
import com.ymkj.smi.manager.common.ex.JsonException;
import com.ymkj.smi.manager.common.untils.CacherContainer;
import com.ymkj.smi.manager.common.untils.RSAUtil;
import com.ymkj.smi.manager.common.untils.XXTeaUtil;
import com.ymkj.smi.manager.config.KeyParamBean;
import com.ymkj.smi.manager.config.SwitchParamBean;
import com.ymkj.smi.manager.web.api.model.req.ReqHeadParam;
import com.ymkj.smi.manager.web.api.model.req.ReqMain;
import com.ymkj.smi.manager.web.api.model.req.ReqParam;

@Slf4j
@Component
public class VersionValidationInterceptor extends HandlerInterceptorAdapter {

    private static final String PKG_NAME_MODEL = "com.ymkj.smi.manager.web.api.model.base";

    @Autowired
    private SwitchParamBean switchParamBean;

    @Autowired
    private KeyParamBean keyParamBean;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
        String keySource = null;
        List<String> errors = (List<String>)obj; // 为了创建filter list
        try {
            if (!SwitchParamBean.SWITCH_OFF.equals(switchParamBean.getAppVersionSwitch())) {
                String functionId = request.getParameter("arg0");
                String param = request.getParameter("arg1");
                String key = request.getParameter("arg2");

                keySource = decriptKey(key);
                String paramSource = decriptParam(keySource, param);

                Class<?> paramClass = Class.forName(PKG_NAME_MODEL + ".Model_" + functionId);

                ReqMain reqMain = JSON.parseObject(paramSource, ReqMain.class);
                String checkReqMainResult = checkBasicFields(reqMain);
                if (checkReqMainResult.trim().length() > 0) {
                    validateFailed(checkReqMainResult, errors);
                    log.error("验证基本请求参数：" + checkReqMainResult);
                    return false;
                }

                String reqParamJson = JSON.parseObject(paramSource).getString("reqParam");
                reqMain.setReqParam((ReqParam) JSON.parseObject(reqParamJson, paramClass));

                ReqHeadParam headParam = reqMain.getReqHeadParam();
                String checkAppMsg = checkAppVersion(headParam);
                if (checkAppMsg.trim().length() > 0) {
                    validateFailed(checkAppMsg, errors);
                    log.error("验证APP版本号：" + checkAppMsg);
                    return false;
                }
            }
        } catch (Exception e) {
            String errorMsg = JsonException.toJson(BussErrorCode.ERROR_CODE_0102);
            log.error(errorMsg, e);
            try {
                validateFailed(errorMsg, errors);
            } catch (Exception e1) {
                log.error(errorMsg, e1);
            }
            return false;
        }
        return true;
    }

    private void validateFailed(String errorMsg, List<String> errorObj){
        errorObj.add(errorMsg);
    }


    private String checkBasicFields(ReqMain reqMain) {
        if (reqMain == null || reqMain.getReqHeadParam() == null) {
            return "reqMain或者reqHeadParam为空.";
        }
        return "";
    }

    public SwitchParamBean getSwitchParamBean() {
        return switchParamBean;
    }

    public void setSwitchParamBean(SwitchParamBean switchParamBean) {
        this.switchParamBean = switchParamBean;
    }

    public KeyParamBean getKeyParamBean() {
        return keyParamBean;
    }

    public void setKeyParamBean(KeyParamBean keyParamBean) {
        this.keyParamBean = keyParamBean;
    }

    private String checkAppVersion(ReqHeadParam headParam) {
        String userAgent = headParam.getUserAgent();
        String deviceType = "";
        if (StringUtils.isBlank(userAgent) || StringUtils.isBlank(headParam.getVersion())) {
            return JsonException.toJson(BussErrorCode.ERROR_CODE_0103);
        }


        String vers[] = headParam.getVersion().split("\\.");
        if (vers.length < 3) {
            return JsonException.toJson(BussErrorCode.ERROR_CODE_0103);
        }
        if (userAgent.toUpperCase().indexOf("IOS") >= 0) {
            deviceType = "ios";
        } else if (userAgent.toUpperCase().indexOf("ANDROID") >= 0) {
            deviceType = "android";
        } else if (userAgent.toUpperCase().indexOf("WAP") >= 0) {
            deviceType = "wap";
            return "";
        } else if (userAgent.toUpperCase().indexOf("WEB") >= 0) {
            deviceType = "web";
            return "";
        } else {
            return JsonException.toJson(BussErrorCode.ERROR_CODE_0103);
        }

//        log.info("*************************版本验证*************************");
//        log.info("苹果版本："+ CacherContainer.getParamValue("appVersion", "ios"));
//        log.info("安卓版本："+CacherContainer.getParamValue("appVersion", "android"));
//        log.info("APP系统："+deviceType);
//        log.info("APP版本："+headParam.getVersion());


        for (Dictionary param : CacherContainer.sysSysParameterMap.get("appVersion")) {
            if (deviceType.equals(param.getDataName())) {
                String prValue = param.getDataValue();
                String prvs[] = prValue.split("\\.");
                if (Integer.parseInt(prvs[0]) > Integer.parseInt(vers[0])) {
                    return JsonException.toJson(BussErrorCode.ERROR_CODE_2100);
                } else if (Integer.parseInt(prvs[0]) == Integer.parseInt(vers[0])) {
                    if (Integer.parseInt(prvs[1]) > Integer.parseInt(vers[1])) {
                        return JsonException.toJson(BussErrorCode.ERROR_CODE_2100);
                    } else if (Integer.parseInt(prvs[1]) == Integer.parseInt(vers[1])) {
                        if (Integer.parseInt(prvs[2]) == Integer.parseInt(vers[2])) {
                            return "";
                        } else if (Integer.parseInt(prvs[2]) < Integer.parseInt(vers[2])) {
                            return "";
                        } else {
                            return JsonException.toJson(BussErrorCode.ERROR_CODE_2100);
                        }
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
        }
        return JsonException.toJson(BussErrorCode.ERROR_CODE_2101);
    }

    @SuppressWarnings("unused")
    private void validateFailed(HttpServletResponse response, String errrMsg, String keySource) throws IOException{
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(encriptResponse(errrMsg, keySource));
        printWriter.flush();
    }

    /**
     * 对响应 APP 的结果进行加密
     * @return
     * @throws Exception
     */
    private String encriptResponse(String errrMsg, String key) {
        if (!SwitchParamBean.SWITCH_OFF.equals(switchParamBean.getXxteaEncriptSwitch())) {
            errrMsg = XXTeaUtil.xxteaEncrypt(errrMsg, key);
        }
        return errrMsg;
    }

    /**
     * 解密 业务参数，在解密业务参数之前，首先要解密key
     * @return
     * @throws Exception
     */
    private String decriptParam(String keySource, String param) throws Exception {
        if (!SwitchParamBean.SWITCH_OFF.equals(switchParamBean.getXxteaEncriptSwitch())) {
            return XXTeaUtil.xxteaDecrypt(param, keySource);
        }
        return param;
    }

    /**
     * 对参数的 key 进行解密操作，该 key 将被用于XXTEA 算法的解密工作
     * @param key
     * @return
     * @throws Exception
     */
    private String decriptKey(String key) throws Exception {
        if (!SwitchParamBean.SWITCH_OFF.equals(switchParamBean.getXxteaEncriptSwitch())) {
            String keyDecripted = RSAUtil.rsaDecrypt(key);
            return keyDecripted;
        }
        return key;
    }


}
