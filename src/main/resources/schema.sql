CREATE TABLE IF NOT EXISTS USERS (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role ENUM('USER', 'ADMIN', 'SYS_ADMIN') NOT NULL
);

CREATE TABLE IF NOT EXISTS SHEET_DATA (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     json_data CLOB NOT NULL
);

CREATE TABLE IF NOT EXISTS DIC_REQ (
                                    dic_req_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    req_usr_nm VARCHAR(50) NOT NULL,
                                    req_dttm TIMESTAMP,
									sts CHAR(1) DEFAULT 'C',
									acpt_sts VARCHAR(50) /* REQ, HOLD, ACPT, DEL */
);

CREATE TABLE IF NOT EXISTS DIC_REQ_DTL (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       dic_req_id INT,
                                       multlang_ccd VARCHAR2(50) NOT NULL,
                                       multlang_key VARCHAR2(500) NOT NULL,
									   reg_sts VARCHAR(50)	/* APPROVAL, REJECT */
);



CREATE TABLE IF NOT EXISTS MULTLANG (
	                                    multlang_ccd VARCHAR2(50) NOT NULL,
										multlang_key VARCHAR2(500) NOT NULL,
										multlang_transl_cont VARCHAR2(500),
										multlang_mod_dttm TIMESTAMP,
										multlang_transl_cont_abbr VARCHAR2(100),
										multlang_abbr_use_yn CHAR(1),
										multlang_typ VARCHAR2(100) NOT NULL,
										rmk VARCHAR2(1000),
										sts CHAR(1) DEFAULT 'C',
										regr_id VARCHAR2(50),
										reg_dttm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
										modr_id VARCHAR2(50),
										mod_dttm TIMESTAMP,
										multlang_transl_fnl_cont VARCHAR2(500)
);


