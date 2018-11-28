package com.ymkj.smi.manager.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ymkj.smi.manager.common.entity.AdminUser;
import com.ymkj.smi.manager.common.entity.Function;
import com.ymkj.smi.manager.common.entity.Role;
import com.ymkj.smi.manager.service.SecurityService;



/**
 * 登录成功后保存用户权限
 * 
 * @author <a href="mailto:longjw@zendaimoney.com">龙建伟</a>
 * @version 0.1 (2015-11-05)
 * @since 0.1
 * @see
 */
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private SecurityService securityService;
	
	@Override
	public UserDetails loadUserByUsername(String jno)
			throws UsernameNotFoundException {
		AdminUser staff=securityService.getStaffByStaffNo(jno);
		return new User(staff.getJno(), staff.getPassword(), true, true, true,
				true,
				// 用户拥有的资源权限
				obtionGrantedAuthorities(staff));
	}

	/**
	 *   取得用户的资源权限
	 * 
	 * @author longjw
	 * @date 2014-8-6 下午02:26:23
	 * @param user
	 * @return 
	 * @since 0.1
	 * @see
	 */
	private Set<GrantedAuthority> obtionGrantedAuthorities(AdminUser user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		
		List<Role> roleList=securityService.getRoleByJno(user.getJno());
		for(Role role:roleList){
			List<Function> menuList=securityService.getMenuByRoleId(String.valueOf(role.getId()));
			for(Function fn:menuList){
				authSet.add(new SimpleGrantedAuthority("ROLE_"+ fn.getFCode()));
			}
		}
		
		//authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authSet;
	}

}
