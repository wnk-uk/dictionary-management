CREATE TABLE IF NOT EXISTS USERS (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role ENUM('USER', 'ADMIN', 'SYS_ADMIN') NOT NULL,
                                     DEPT_NM VARCHAR(50),
                                     USR_NM VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS SHEET_DATA (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     json_data CLOB NOT NULL
);

CREATE TABLE IF NOT EXISTS DIC_REQ (
                                    dic_req_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                     multlang_ccd VARCHAR2(50) NOT NULL,
--                                     multlang_key VARCHAR2(500) NOT NULL,
--                                     reg_sts VARCHAR(50),	/* APPROVAL, REJECT */
                                    REQ_USR_NM VARCHAR(50),
                                    REQ_DTTM TIMESTAMP,
                                    STS CHAR(1) DEFAULT 'C',
                                    ACPT_STS VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS DIC_REQ_DTL (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 상세 요청 ID (PK)
                                    dic_req_id INT,
                                    multlang_ccd VARCHAR(50) NOT NULL, -- 언어 코드 (예: EN, KR)
                                    multlang_key VARCHAR(500) NOT NULL, -- 기존 키
                                    multlang_transl_cont VARCHAR(500), -- 새로운 번역
                                    multlang_transl_cont_abbr VARCHAR(100), -- 새로운 약어
                                    multlang_typ VARCHAR(100), -- 단어 유형
                                    screen_path VARCHAR(255), -- 화면 경로
                                    source_path VARCHAR(255), -- 소스 코드 경로
                                    comment VARCHAR(1000), -- 요청자의 코멘트
                                    reg_sts VARCHAR(50) DEFAULT 'PENDING', -- 등록 상태 (PENDING, APPROVAL, REJECT)
                                    FOREIGN KEY (dic_req_id) REFERENCES DIC_REQ(dic_req_id) ON DELETE CASCADE
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


