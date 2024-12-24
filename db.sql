CREATE TABLE `kkr_agent_master` (
  `agent_id` int NOT NULL AUTO_INCREMENT,
  `agent_name` varchar(45) DEFAULT NULL,
  `role_id` int NOT NULL,
  `merchant_id` int NOT NULL,
  `company_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  `created_by` varchar(95) DEFAULT NULL,
  `updated_by` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`agent_id`),
  KEY `fk_role_id_idx` (`role_id`),
  KEY `fk_merchant_id_idx` (`merchant_id`),
  KEY `fk_company_id_idx` (`company_id`),
  CONSTRAINT `fk_company_id` FOREIGN KEY (`company_id`) REFERENCES `kkr_company_master` (`company_id`),
  CONSTRAINT `fk_merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `kkr_merchant_master` (`merchant_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `kkr_role_master` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


___________________________________________________________



CREATE TABLE `kkr_audit_master` (
  `audit_id` int NOT NULL AUTO_INCREMENT,
  `company_id` int NOT NULL,
  `merchant_id` int NOT NULL,
  `agent_id` int NOT NULL,
  `player_id` int NOT NULL,
  `event_type` varchar(45) DEFAULT NULL,
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  PRIMARY KEY (`audit_id`),
  KEY `fk_company_id_idx` (`company_id`),
  KEY `fk_merchant_id_idx` (`merchant_id`),
  KEY `fk_agent_id_idx` (`agent_id`),
  KEY `fk_player_id_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


___________________________________________________________

CREATE TABLE `kkr_company_master` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(45) DEFAULT NULL,
  `role_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  `created_by` varchar(95) DEFAULT NULL,
  `updated_by` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  KEY `fk_role_id_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

___________________________________________________________


CREATE TABLE `kkr_merchant_master` (
  `merchant_id` int NOT NULL AUTO_INCREMENT,
  `merchant_name` varchar(45) DEFAULT NULL,
  `company_id` int NOT NULL,
  `role_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  `created_by` varchar(95) DEFAULT NULL,
  `updated_by` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`merchant_id`),
  KEY `roleId_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

___________________________________________________________


CREATE TABLE `kkr_player` (
  `player_id` int NOT NULL AUTO_INCREMENT,
  `player_name` varchar(45) DEFAULT NULL,
  `role_id` int NOT NULL,
  `agent_id` int NOT NULL,
  `merchant_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  `created_by` varchar(95) DEFAULT NULL,
  `updated_by` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`player_id`),
  KEY `fk_role_id_idx` (`role_id`),
  KEY `fk_agent_id_idx` (`agent_id`),
  KEY `fk_merchant_id_idx` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci



___________________________________________________________


CREATE TABLE `kkr_role_master` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(95) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `creation_time` varchar(255) NOT NULL,
  `last_update_time` varchar(255) NOT NULL,
  `created_by` varchar(95) DEFAULT NULL,
  `updated_by` varchar(95) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

___________________________________________________________


CREATE TABLE `kkr_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `creation_time` timestamp NOT NULL,
  `last_update_time` timestamp NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_role_id_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci