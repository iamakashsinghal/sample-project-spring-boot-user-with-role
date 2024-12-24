package com.loma.kkr.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.loma.kkr.common.dao.LoginReq;
import com.loma.kkr.common.dao.UserInfoDto;
import com.loma.kkr.common.dao.UserRegisterResponse;
import com.loma.kkr.common.dao.UserReturn;
import com.loma.kkr.common.dao.UserSignInResponse;
import com.loma.kkr.common.entity.UserAuditLogs;

import jakarta.servlet.http.HttpServletRequest;

/**
 * This User Info Service interface have a abstract method which is access 
 * User Info Controller
 * @author Akash
 */
@Service
public interface UserInfoService {
	
	/**
	 * To add User in User Info table 
	 * @param userInfo
	 * @return UserRegisterResponse
	 */
	UserRegisterResponse addUsers(UserInfoDto userInfoDto);
	
	/**
	 * User Login get details from UserInfo
	 * @param loginReq
	 * @param httpServletRequest 
	 * @return UserSignInResponse
	 */
	UserSignInResponse userLogin(LoginReq loginReq, HttpServletRequest httpServletRequest);
	
	/**
	 * To provide All User List
	 * @return List of UserReturn
	 */
	List<UserReturn> getAllUserList();
	
	/**
	 * To provide User details by user Id
	 * @param id
	 * @return UserSignInResponse
	 */
	UserSignInResponse getUserById(long id);
	
	/**
	 * To Update User details by user info
	 * @param userInfoDto
	 * @return String
	 */
	String updateUserById(UserInfoDto userInfoDto);
	
	/**
	 * To De-Active User details by user info
	 * @param userId
	 * @return String
	 */
	String deactiveUserById(Long userId);
	
	/**
	 * To provide All User Audit Logs List
	 * @return List of UserAuditLogs
	 */
	List<UserAuditLogs> getAllAuditLogs();

}
