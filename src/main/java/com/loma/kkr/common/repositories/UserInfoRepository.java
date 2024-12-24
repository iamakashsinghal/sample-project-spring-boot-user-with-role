package com.loma.kkr.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loma.kkr.common.entity.UserInfo;

/**
 * User Info JPA Repository which is mapped with User Info Table
 * @author Akash
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	
	/**
	 * To pass user email then get this the data from User Info Table
	 * @param email
	 * @return UserInfo
	 */
	UserInfo findByUserEmail(String email);
	
	/**
	 * To pass user id then get this the data from User Info Table
	 * @param id
	 * @return UserInfo
	 */
	UserInfo findByUserId(long id);

}
