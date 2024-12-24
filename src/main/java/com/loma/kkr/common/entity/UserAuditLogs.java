package com.loma.kkr.common.entity;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Role Master Entity class with mapped with data base table kkr_role_master
 * 
 * @author Akash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "kkr_user_audit_logs")
public class UserAuditLogs {

	/**
	 * Audit Id is Auto Generated It is Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "audit_id")
	private Long auditId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "login_name")
	private String loginName;

	@Column(name = "ip_address")
	private String iPAddress;

	private String host;

	private int port;

	@Column(name = "login_mode")
	private String loginMode;
	
	/**
	 * Provider is use to Store Login Type 
	 * Like M-> Manual, G-> Google, F- Facebook
	 */
	private String provider;

	private String os;
	private String browser;

	@CreationTimestamp
	@Column(name = "creation_time")
	private String creationTime;

}
