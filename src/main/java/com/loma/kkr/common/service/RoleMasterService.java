package com.loma.kkr.common.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.loma.kkr.common.dao.RoleMasterReturn;
import com.loma.kkr.common.entity.RoleMaster;

/**
 * This Role Master Service interface have a abstract method which is access 
 * Role Master Controller
 * @author Akash
 */
@Service
public interface RoleMasterService {
	
	/**
	 * To add Role in Role Master table 
	 * @param roleMaster
	 */
	RoleMaster addRoleMaster(RoleMaster roleMaster);
	
	/**
	 * To Update by Role in Master table
	 * @param roleMaster
	 * @return String
	 */
	String updateByRoleId(RoleMaster roleMaster);
	
	/**
	 * To Delete by Role in Master table
	 * @param roleId
	 * @return String
	 */
	String deleteByRoleId(Long roleId);
	
	/**
	 * To Get All Role List in Master table
	 * @return userReturnList
	 */
	List<RoleMasterReturn> getAllRoleList();
}
