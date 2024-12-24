package com.loma.kkr.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loma.kkr.common.dao.RoleMasterReturn;
import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.service.RoleMasterService;

/**
 * This Controller is mainly use to maintain the Role Master Details
 * 
 * @author Akash
 */
@RestController
@RequestMapping("/api/role")
public class RoleMasterController {

	/**
	 * To Autowired Role Master Service to perfume business login
	 */
	@Autowired
	private RoleMasterService roleMasterService;

	/**
	 * To Add Roll in Master Roll Table
	 * 
	 * @param roleMaster
	 * @return RoleMaster
	 */
	@PostMapping("/addRole")
	public RoleMaster addRole(@RequestBody RoleMaster roleMaster) {
		return roleMasterService.addRoleMaster(roleMaster);
	}
	
	/**
	 * To provide All Role List
	 * @return List of UserReturn
	 */	
	@GetMapping(value = "/roleList")
	public ResponseEntity<List<RoleMasterReturn>> getAllUserList() {
		List<RoleMasterReturn> roleMasterReturnList = roleMasterService.getAllRoleList();
		return new ResponseEntity<>(roleMasterReturnList, HttpStatus.OK);
	}

	/**
	 * To Update Role details by Role info
	 * @param roleMaster
	 * @return response
	 */
	@PutMapping(value = "/updateRole")
	public String updateUserById(@RequestBody RoleMaster roleMaster) {
		String response = roleMasterService.updateByRoleId(roleMaster);
		return response;
	}
	
	/**
	 * To Delete Role details by Role info
	 * @param roleId
	 * @return response
	 */
	@DeleteMapping("/roleDelete/{id}")
	public String deleteByRoleId(@PathVariable("id") Long roleId) {
		String response = roleMasterService.deleteByRoleId(roleId);
		return response;
	}
}
