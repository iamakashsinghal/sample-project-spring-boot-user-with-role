package com.loma.kkr.common.dao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * This is class is Base Request Based
 * @author Akash
 */
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3944033965447986572L;

	@Getter
	@Setter
	@JsonIgnore(value = true)
	private String userId;
}
