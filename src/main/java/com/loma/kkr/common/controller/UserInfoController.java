package com.loma.kkr.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loma.kkr.common.dao.LoginReq;
import com.loma.kkr.common.dao.UserInfoDto;
import com.loma.kkr.common.dao.UserRegisterResponse;
import com.loma.kkr.common.dao.UserReturn;
import com.loma.kkr.common.dao.UserSignInResponse;
import com.loma.kkr.common.entity.UserAuditLogs;
import com.loma.kkr.common.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;

/** 
 * UserInfo Controller class is use to many operation like Login User, Add User, User List, Update User etc.
 * @author Akash
 */

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
	
	/**
	 * To Autowired User Info Service to perfume business login
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * To Add User in User Info Table 
	 * @param userInfoDto
	 * @return UserRegisterResponse
	 */
	@PostMapping("/addUsers")
	public ResponseEntity<UserRegisterResponse> addUsers(@RequestBody UserInfoDto userInfoDto) {
		return new ResponseEntity<UserRegisterResponse>(userInfoService.addUsers(userInfoDto), HttpStatus.OK);
	}
	
	/**
	 * User Login Api 
	 * @param LoginReq, HttpServletRequest
	 * @return UserSignInResponse
	 */
	@PostMapping("/userLogin")
	public ResponseEntity<UserSignInResponse> userLogin(@RequestBody LoginReq loginReq, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<UserSignInResponse>(userInfoService.userLogin(loginReq, httpServletRequest), HttpStatus.OK);
	}
	
	/**
	 * To provide All User List
	 * @return List of UserReturn
	 */
	@PreAuthorize("hasRole('SA')")
	@GetMapping(value = "/userList")
	public ResponseEntity<List<UserReturn>> getAllUserList() {
		List<UserReturn> userReturnList = userInfoService.getAllUserList();
		return new ResponseEntity<>(userReturnList, HttpStatus.OK);
	}
	
	/**
	 * To provide User details by user Id
	 * @param id
	 * @return UserSignInResponse
	 */
	@GetMapping(value="/getUser/{id}")
    public ResponseEntity<UserSignInResponse> getUserById(@PathVariable long id){
		return new ResponseEntity<UserSignInResponse>(userInfoService.getUserById(id),
				HttpStatus.OK);
    }
	
	/**
	 * To Update User details by user info
	 * @param userInfoDto
	 * @return response
	 */
	@PutMapping(value="/updateUser")
    public String updateUserById(@RequestBody UserInfoDto userInfoDto) {
		String response = userInfoService.updateUserById(userInfoDto);
		return response;
	}
	
	/**
	 * To de-active User By Id details by user info
	 * @param userId
	 * @return response
	 */
	@PutMapping(value="/deactiveUserById/{id}")
    public String deactiveUserById(@PathVariable("id") Long userId) {
		String response = userInfoService.deactiveUserById(userId);
		return response;
	}
	
	/**
	 * To provide All User Audit Logs List
	 * @return List of UserAuditLogs
	 */
	@GetMapping(value = "/auditLogsList")
	public ResponseEntity<List<UserAuditLogs>> getAllAuditLogs() {
		List<UserAuditLogs> userAuditLogsList = userInfoService.getAllAuditLogs();
		return new ResponseEntity<>(userAuditLogsList, HttpStatus.OK);
	}
}
