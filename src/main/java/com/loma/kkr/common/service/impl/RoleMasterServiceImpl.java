package com.loma.kkr.common.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loma.kkr.common.dao.RoleMasterReturn;
import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.repositories.RoleMasterRepository;
import com.loma.kkr.common.service.RoleMasterService;
import com.loma.kkr.common.util.MessageConstants;
import com.loma.kkr.common.util.ObjectMapper;;

/**
 * Role Master Service implementation class to define body and implement
 * business logic According to business requirement
 * 
 * @author Akash
 */
@Service
@Transactional
public class RoleMasterServiceImpl implements RoleMasterService {

	/**
	 * To Autowired Role Master Repository to perfume database operations
	 */
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	/**
	 * To User Mapper class to object mapped
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * To Add Role in Master table
	 * 
	 * @param roleMaster
	 */
	@Override
	public RoleMaster addRoleMaster(RoleMaster roleMaster) {
		return roleMasterRepository.save(roleMaster);
	}
	
	/**
	 * To Update by Role in Master table
	 * @param roleMaster
	 * @return roleMasterResponse
	 */
	@Override
	public String updateByRoleId(RoleMaster roleMaster) {

		RoleMaster dbRoleRecord = roleMasterRepository.findByRoleId(roleMaster.getRoleId());
		String roleMasterResponse;
		
		if (dbRoleRecord == null) {
			return roleMasterResponse = MessageConstants.ROLE_ID_NOT_EXISTS_KEY;
		}

		if (dbRoleRecord.getStatus() == 1) {
			return roleMasterResponse = MessageConstants.ROLE_IS_NOT_ACTIVE_KEY;
		}

		dbRoleRecord.setRoleName(roleMaster.getRoleName());
		dbRoleRecord.setCreatedBy(roleMaster.getCreatedBy());
		dbRoleRecord.setLastUpdateTime(roleMaster.getLastUpdateTime());
		roleMasterRepository.save(dbRoleRecord);
		roleMasterResponse = MessageConstants.SUCCESS_KEY;

		return roleMasterResponse;
	}
	
	/**
	 * To Delete by Role in Master table
	 * @param roleMaster
	 * @return roleMasterResponse
	 */
	@Override
	public String deleteByRoleId(Long roleId) {
		RoleMaster dbRoleRecord = roleMasterRepository.findByRoleId(roleId);
		if (dbRoleRecord == null) {
			return MessageConstants.NOT_EXIST_KEY;
		}
		int value = roleMasterRepository.deleteByRoleId(dbRoleRecord.getRoleId());
		if (value == 1) 
            return MessageConstants.SUCCESS_KEY;
		else
			return MessageConstants.FAILED_KEY;
	}
	
	/**
	 * To Get All Role List in Master table
	 * @return userReturnList
	 */
	@Override
	public List<RoleMasterReturn> getAllRoleList() {
		List<RoleMasterReturn> userReturnList = roleMasterRepository.findAll().stream()
				.map(userReturn -> objectMapper.roleMapperToDto(userReturn)).collect(Collectors.toList());

		return userReturnList;
	}

}
