package com.loma.kkr.common.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.loma.kkr.common.config.JwtUtil;
import com.loma.kkr.common.dao.LoginReq;
import com.loma.kkr.common.dao.UserAuthResponseData;
import com.loma.kkr.common.dao.UserInfoDto;
import com.loma.kkr.common.dao.UserRegisterResponse;
import com.loma.kkr.common.dao.UserReturn;
import com.loma.kkr.common.dao.UserSignInResponse;
import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.entity.UserAuditLogs;
import com.loma.kkr.common.entity.UserInfo;
import com.loma.kkr.common.exception.BusinessException;
import com.loma.kkr.common.exception.NoDataFoundException;
import com.loma.kkr.common.exception.UserNotFoundException;
import com.loma.kkr.common.repositories.RoleMasterRepository;
import com.loma.kkr.common.repositories.UserAuditLogsRepositories;
import com.loma.kkr.common.repositories.UserInfoRepository;
import com.loma.kkr.common.service.UserInfoService;
import com.loma.kkr.common.util.AppBeanUtils;
import com.loma.kkr.common.util.MessageConstants;
import com.loma.kkr.common.util.ObjectMapper;
import com.loma.kkr.common.util.enums.AppStatusCodes;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

/**
 * User Info Service implementation class to define body and implement business
 * logic According to business requirement
 * 
 * @author Akash
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	private static final Log log = LogFactory.getLog(UserInfoServiceImpl.class);

	/**
	 * To Autowired User Info Repository to perfume database operations
	 */
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * To User Mapper class to object mapped
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * To Authentication Manager Interface to authenticate jwt details
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Jwt Utility class is user to create token
	 */
	private JwtUtil jwtTokenUtils;

	/**
	 * For Password Encoder is to encode this password
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * To Autowired Role Master Repository to perfume database operations
	 */
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private UserAuditLogsRepositories userAuditLogsRepositories;

	public UserInfoServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtils = jwtTokenUtils;

	}

	/**
	 * To Message Source to access custom message value
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * To Add User in User Info Table
	 * 
	 * @param userInfo
	 * @return UserRegisterResponse
	 */
	@Override
	public UserRegisterResponse addUsers(UserInfoDto userInfo) {

		String email = userInfo.getUserEmail();

		// 1. Validate account with email
		UserInfo dbUserRecord = userInfoRepository.findByUserEmail(email);
		if (dbUserRecord != null) {
			log.error("Account already exists for email: " + email);
			throw new BusinessException(HttpStatus.CONFLICT, AppStatusCodes.USER_EMAIL_ALREADY_EXISTS.value(),
					MessageConstants.USER_EMAIL_ALREADY_EXISTS_KEY);
		}

		// 2.1 Create User Account
		dbUserRecord = new UserInfo();
		dbUserRecord.setUserEmail(userInfo.getUserEmail());
		dbUserRecord.setUserName(userInfo.getUserName());
		dbUserRecord.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));

		List<RoleMaster> roleMasters = new ArrayList<>();
		for (Long pjtId : userInfo.getRoleId()) {
			RoleMaster tempRoleId = roleMasterRepository.findByRoleId(pjtId);
			roleMasters.add(tempRoleId);
		}
		dbUserRecord.setRoleMaster(new HashSet<>(roleMasters));
		dbUserRecord.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
		dbUserRecord = userInfoRepository.save(dbUserRecord);

		// 2.2 Generate Access Token
		String accessToken = generateAccessToken(email, dbUserRecord.getUserPassword(), false);
		log.debug("Generated JWT Access Token: " + accessToken);

		UserReturn userReturn = new UserReturn();
		AppBeanUtils.copyNonNullProperties(dbUserRecord, userReturn);

		UserRegisterResponse userRegisterResponse = new UserRegisterResponse(AppStatusCodes.CREATED.value(),
				messageSource.getMessage(MessageConstants.USER_REGISTRATION_SUCCESS_KEY, null, Locale.getDefault()),
				new UserAuthResponseData(accessToken, userReturn));

		return userRegisterResponse;

	}

	/**
	 * User Login Api
	 * 
	 * @param loginReq
	 * @return UserSignInResponse
	 */
	@Override
	public UserSignInResponse userLogin(LoginReq loginReq, HttpServletRequest httpServletRequest) {
		String email = loginReq.getEmail();
		
		UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader("User-Agent"));
		UserAuditLogs userLogs = new UserAuditLogs();
		log.debug("Authenticating user credentials");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginReq.getPassword()));
		} catch (BadCredentialsException exp) {
			throw new NoDataFoundException(email);

		} catch (Exception exp) {
			log.error("No Account exists for email: " + email, exp);
			throw new UserNotFoundException(email);
		}
		UserInfo dbUserRecord = userInfoRepository.findByUserEmail(email);
		
		if (dbUserRecord.getStatus() == 1) {
			log.error("User is De-Active");
			 throw new BusinessException(HttpStatus.GONE, AppStatusCodes.USER_IS_NOT_ACTIVE.value(),
					MessageConstants.USER_IS_NOT_ACTIVE_KEY);
		}
		
		List<RoleMaster> roleMasters = roleMasterRepository.getRoleData(dbUserRecord.getUserId());
		
		
		String accessToken = generateAccessToken(email, dbUserRecord.getUserPassword(), false);
		log.debug("Generated JWT Access Token: " + accessToken);
		
		userLogs.setBrowser(userAgent.getBrowser().getName());
		userLogs.setIPAddress(httpServletRequest.getRemoteHost());
		userLogs.setHost(httpServletRequest.getRemoteHost());
		userLogs.setLoginMode(userAgent.getOperatingSystem().getDeviceType().name());
		userLogs.setLoginName(dbUserRecord.getUserEmail());
		userLogs.setOs(userAgent.getOperatingSystem().getName());
		userLogs.setPort(httpServletRequest.getRemotePort());
		userLogs.setProvider("M");
		userLogs.setUserId(dbUserRecord.getUserId());
		
		userAuditLogsRepositories.save(userLogs);
		
		UserReturn userReturn = new UserReturn();
		userReturn.setRoleMaster(roleMasters);
		AppBeanUtils.copyNonNullProperties(dbUserRecord, userReturn);

		userInfoRepository.save(dbUserRecord);
		UserSignInResponse userSignInResponse = new UserSignInResponse(AppStatusCodes.SUCCESS.value(),
				messageSource.getMessage(MessageConstants.USER_LOGIN_SUCCESS_KEY, null, Locale.getDefault()),
				new UserAuthResponseData(accessToken, userReturn));

		return userSignInResponse;
	}

	/**
	 * To provide All User List
	 * 
	 * @return List of UserReturn
	 */
	@Override
	public List<UserReturn> getAllUserList() {
		List<UserReturn> userReturnList = userInfoRepository.findAll().stream()
				.map(userReturn -> objectMapper.userMapperToDto(userReturn)).collect(Collectors.toList());

		return userReturnList;
	}

	/**
	 * To provide User details by user Id
	 * 
	 * @param id
	 * @return UserSignInResponse
	 */
	@Override
	public UserSignInResponse getUserById(long id) {

		UserInfo dbUserRecord = userInfoRepository.findByUserId(id);
		if (dbUserRecord == null) {
			log.error("No User record found for id");
			throw new BusinessException(HttpStatus.BAD_REQUEST, AppStatusCodes.USER_ID_NOT_EXISTS.value(),
					MessageConstants.USER_ID_NOT_EXISTS_KEY);
		}

		UserReturn userReturn = new UserReturn();
		AppBeanUtils.copyNonNullProperties(dbUserRecord, userReturn);

		return UserSignInResponse.builder().code(AppStatusCodes.SUCCESS.value())
				.message(messageSource.getMessage(MessageConstants.USER_SUCCESS_KEY, null, Locale.getDefault()))
				.data(new UserAuthResponseData(null, userReturn)).build();

	}

	/**
	 * To Update User details by user info
	 * 
	 * @param userInfo
	 * @return userUpdateResponse
	 */
	@Override
	public String updateUserById(UserInfoDto userInfo) {
		String email = userInfo.getUserEmail();

		UserInfo dbUserRecord = userInfoRepository.findByUserEmail(email);
		String userUpdateResponse;
		
		if (dbUserRecord == null) {
			return userUpdateResponse = MessageConstants.USER_ID_NOT_EXISTS_KEY;
		}

		if (dbUserRecord.getStatus() == 1) {
			return userUpdateResponse = MessageConstants.USER_IS_NOT_ACTIVE_KEY;
		}
		
		List<RoleMaster> roleMasters = new ArrayList<>();
		for (Long pjtId : userInfo.getRoleId()) {
			RoleMaster tempRoleId = roleMasterRepository.findByRoleId(pjtId);
			roleMasters.add(tempRoleId);
		}
		
		dbUserRecord.setUserEmail(userInfo.getUserEmail());
		dbUserRecord.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
		dbUserRecord.setUserName(userInfo.getUserName());
		dbUserRecord.setRoleMaster(new HashSet<>(roleMasters));
		userInfoRepository.save(dbUserRecord);
		userUpdateResponse = MessageConstants.SUCCESS_KEY;
		
		return userUpdateResponse;

	}
	
	/**
	 * To De-Active User details by user info
	 * 
	 * @param userInfo
	 * @return userdeactiveResponse
	 */
	@Override
	public String deactiveUserById(Long userId) {

		UserInfo dbUserRecord = userInfoRepository.findByUserId(userId);

		String userdeactiveResponse;
		
		if (dbUserRecord == null) {
			userdeactiveResponse = MessageConstants.USER_ID_NOT_EXISTS_KEY;
		}

		if (dbUserRecord.getStatus() == 1) {
			userdeactiveResponse = MessageConstants.USER_IS_ALREADY_DEACTIVE_KEY;
		}
		dbUserRecord.setStatus(1);
		dbUserRecord.setUserEmail(dbUserRecord.getUserEmail());
		dbUserRecord.setLastUpdateTime(dbUserRecord.getLastUpdateTime());
		userInfoRepository.save(dbUserRecord);
		userdeactiveResponse = MessageConstants.SUCCESS_KEY;

		return userdeactiveResponse;
	}
	
	private String generateAccessToken(String email, String password, boolean rememberMe) {
		return jwtTokenUtils.createToken(org.springframework.security.core.userdetails.User.builder().username(email)
				.password(password).build(), rememberMe);
	}
	
	/**
	 * To provide All User Audit Logs List
	 * @return List of UserAuditLogs
	 */
	@Override
	public List<UserAuditLogs> getAllAuditLogs() {
		List<UserAuditLogs> userAuditLogsList = userAuditLogsRepositories.findAll();
		return userAuditLogsList;
	}
}
