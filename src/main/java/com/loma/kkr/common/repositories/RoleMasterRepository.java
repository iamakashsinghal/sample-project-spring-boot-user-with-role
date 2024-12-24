package com.loma.kkr.common.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loma.kkr.common.entity.RoleMaster;

/**
 * Role Master JPA Repository which is mapped with Role Master Table
 * @author Akash
 */
@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {
	
	/**
	 * To pass role id then get this the data from Role Master Table
	 * @param roleId
	 * @return
	 */
	RoleMaster findByRoleId(Long roleId);
	
	/**
	 * For Mapping Many to Many table Native Query 
	 * @param UserId
	 * @return RoleMaster
	 */
	@Query(value = "SELECT * FROM kkr_role_master bs WHERE role_id in (SELECT role_id FROM kkr_user_roles c WHERE c.user_id = :user_id) ",
            nativeQuery = true)
    RoleMaster queryBy(@Param("user_id") long UserId);
	
	/**
	 * For Mapping Many to Many table Native Query 
	 * @param UserId
	 * @return List RoleMaster
	 */
	@Query(value = "SELECT * FROM kkr_role_master bs WHERE role_id in (SELECT role_id FROM kkr_user_roles c WHERE c.user_id = :user_id) ",
            nativeQuery = true)
    List<RoleMaster> getRoleData(@Param("user_id") long UserId);
	
	/**
	 * Delete Role Record 
	 * @param roleId
	 * @return int like 1,0
	 */
	int deleteByRoleId(Long roleId);

}
