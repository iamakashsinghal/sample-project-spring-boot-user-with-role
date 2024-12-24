package com.loma.kkr.common.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Info Entity class with mapped with data base table kkr_user_info
 * 
 * @author Akash
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "kkr_user_info")
public class UserInfo {

	/**
	 * User Id is Auto Generated It is Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;

	/**
	 * User Name is which provide user end
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * User Email is which provide user end
	 */
	@Column(name = "user_email")
	private String userEmail;

	/**
	 * User Password is which provide user end
	 */
	@Column(name = "user_password")
	private String userPassword;

	/**
	 * Many to Many Relationship with Role Master table which is maintain role_id
	 * and user_id
	 */
	@ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		              CascadeType.PERSIST,
		              CascadeType.MERGE
		          })
    @JoinTable(
        name = "kkr_user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleMaster> roleMaster = new HashSet<>();

	/**
	 * Status to check user is Active or De-active
	 */
	@Column(columnDefinition = "tinyint(1) default '0'")
	private int status;

	@CreationTimestamp
	@Column(name = "creation_time")
	private String creationTime;

	@UpdateTimestamp
	@Column(name = "last_update_time")
	private String lastUpdateTime;
	
}
