package com.loma.kkr.common.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Entity(name = "kkr_role_master")
public class RoleMaster {

	/**
	 * Role Id is Auto Generated It is Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * Status to check role is Active or De-active
	 */
	@Column(columnDefinition = "tinyint(1) default '0'")
	private byte status;

	@CreationTimestamp
	@Column(name = "creation_time")
	private String creationTime;

	@UpdateTimestamp
	@Column(name = "last_update_time")
	private String lastUpdateTime;
	
	@JsonIgnore
	@Column(name = "created_by", columnDefinition = "varchar(95) default 'admin'")
	private String createdBy;
	
	@JsonIgnore
	@Column(name = "updated_by", columnDefinition = "varchar(95) default 'admin'")
	private String updatedBy;

	@Column(name = "role_name")
	private String roleName;

	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          }, mappedBy = "roleMaster")
	@JsonIgnore
	private Set<UserInfo> userInfo = new HashSet<>();
}
