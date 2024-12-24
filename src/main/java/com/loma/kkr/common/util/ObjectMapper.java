package com.loma.kkr.common.util;

import org.springframework.stereotype.Service;

import com.loma.kkr.common.dao.RoleMasterReturn;
import com.loma.kkr.common.dao.UserReturn;
import com.loma.kkr.common.entity.RoleMaster;
import com.loma.kkr.common.entity.UserInfo;

/** 
 * This Service class is mapped UserInfo object to User Return Object
 * @author Akash
 */
@Service
public class ObjectMapper {

    /**
     *  We first create our userInfo dto record, and return it
     * @param userInfo
     * @return mappedUserReturnRecord
     */
	public UserReturn userMapperToDto(UserInfo userInfo) {
		UserReturn mappedUserReturnRecord = new UserReturn(
				userInfo.getUserName(),
				userInfo.getUserEmail(),
				null
        );

        return mappedUserReturnRecord;
    }

	public RoleMasterReturn roleMapperToDto(RoleMaster roleMaster) {
		RoleMasterReturn mappedUserReturnRecord = new RoleMasterReturn(
				roleMaster.getRoleId(),
				roleMaster.getRoleName()
        );

        return mappedUserReturnRecord;
	}
	
}
