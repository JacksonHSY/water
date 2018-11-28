package com.ymkj.smi.manager.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.service.SecurityService;



/**
 * 加载资源与权限的对应关系
 * 
 * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作
 * 
 * @author <a href="mailto:longjw@cssweb.sh.cn">龙建伟</a>
 * @version 0.1 (2014-8-6 下午02:08:37)
 * @since 0.1
 * @see
 * */
@Slf4j
@Service
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private SecurityService securityService;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 获取需要的权限
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		log.debug("-----------MySecurityMetadataSource getAttributes ----------- ");
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap == null) {
			loadResourceDefine();
		}
		if(requestUrl.indexOf("?")>-1){
			requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
		}
		log.debug("-----------requestUrl----------- "+requestUrl);
		Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
		if(configAttributes == null){
			configAttributes = new ArrayList<ConfigAttribute>();
			configAttributes.add(new SecurityConfig("ROLE_NO_USER")); 
		}
		return configAttributes;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
	
	/**
	 * 初始化请求与角色对应关系
	 */
	@PostConstruct
	private void loadResourceDefine() {
		log.debug("-----------MySecurityMetadataSource loadResourceDefine ----------- ");
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Function> menus=securityService.getMenuList();
			if(!menus.isEmpty()){
				for (Function menu : menus) {
					Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
					// 通过菜单code来表示具体的权限 注意：必须"ROLE_"开头
					ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + menu.getFCode());
					configAttributes.add(configAttribute);
					resourceMap.put(menu.getUrl(), configAttributes);
					String pageViewUrls= menu.getDescriber();
					if(pageViewUrls!=null && !"".equals(pageViewUrls)){
						String[] pageUrls=pageViewUrls.split(",");
						for(String pageUrl:pageUrls){
							resourceMap.put(pageUrl, configAttributes);
						}
					}
				}
			}
		}
	}
}