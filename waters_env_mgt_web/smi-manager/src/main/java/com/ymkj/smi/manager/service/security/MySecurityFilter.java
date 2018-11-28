package com.ymkj.smi.manager.service.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

/**
 * 自己实现的过滤用户请求类
 * 过滤器入口
 * 
 * 原理：核心的InterceptorStatusToken token = super.beforeInvocation(fi);
 * 会调用定义的accessDecisionManager:decide(Object object)和securityMetadataSource.getAttributes(Object object)方法。
 * 
 * @author Joshua
 */
@Slf4j
@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements
		Filter {

	@Autowired
	private MySecurityMetadataSource securityMetadataSource;
	@Autowired
	private MyAccessDecisionManager accessDecisionManager;
	@Autowired
	private AuthenticationManager myAuthenticationManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.debug("---------------  MySecurityFilter.doFilter ---------------");
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		//安全检查
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@PostConstruct
	public void init() {
		super.setAuthenticationManager(myAuthenticationManager);
		super.setAccessDecisionManager(accessDecisionManager);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public Class<?> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方法必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public void destroy() {
	}

}
