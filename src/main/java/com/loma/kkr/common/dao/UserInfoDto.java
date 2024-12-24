package com.loma.kkr.common.dao;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserInfoDto {
	
	private String userName;
	
	private String userEmail;
	
	private String userPassword;
	
	private List<Long> roleId;
}
