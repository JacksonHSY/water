package com.ymkj.smi.manager.service.security;

import java.util.Collection;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 自定义鉴权
 *                                                
 * @author Joshua     
 */
@Slf4j
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 鉴权
	 * 
	 * @param authentication 用户所拥有的权限
	 * @param configAttributes 请求资源的所需的权限
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		log.debug("---------------  MyAccessDecisionManager.decide ---------------");
		if(configAttributes == null){
			throw new AccessDeniedException("Access is denied");
//			return;
		}
		//所请求的资源拥有的权限
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext()){
			ConfigAttribute configAttribute = iterator.next();
			//访问请求资源所需的权限
			String needPermission = configAttribute.getAttribute();			
			//用户所拥有的权限
			for(GrantedAuthority ga : authentication.getAuthorities()){
				if(needPermission.equals(ga.getAuthority())){
					return;
				}
			}
		}
		throw new AccessDeniedException("Access is denied");
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
