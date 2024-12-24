package com.loma.kkr.common.service.impl;

import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.entity.UserInfo;
import com.loma.kkr.common.repositories.RoleMasterRepository;
import com.loma.kkr.common.repositories.UserInfoRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetails Service Implementation which is store token data
 * @author Akash
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    UserInfoRepository userInfoRepository;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	public CustomUserDetailsServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByUserEmail(email);
        List<RoleMaster> roleMasters = roleMasterRepository.getRoleData(user.getUserId());
        
        List<GrantedAuthority> grantedAuthorities = roleMasters.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRoleName());
		}).collect(Collectors.toList());
        
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUserEmail())
                        .password(user.getUserPassword())
                        //.roles(roleMaster.getRoleName())
                        .authorities(grantedAuthorities)
                        .accountExpired(false)
                        .accountLocked(false)
                        .disabled(false)
                        .credentialsExpired(false)
                        .build();
        return userDetails;
    }
}
