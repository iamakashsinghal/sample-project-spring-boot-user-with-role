package com.loma.kkr.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loma.kkr.common.entity.UserAuditLogs;

/**
 * This interface is use to capture User Audit logs 
 * @author Akash
 */
public interface UserAuditLogsRepositories extends JpaRepository<UserAuditLogs, Long> {

}
