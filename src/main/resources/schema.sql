CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE IF NOT EXISTS sheet_data (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     data CLOB NOT NULL
);

-- CREATE TABLE IF NOT EXISTS MULTLANG (
-- 	                                    MULTLANG_CCD VARCHAR2(50) NOT NULL,
-- 										MULTLANG_KEY VARCHAR2(500) NOT NULL,
-- 										MULTLANG_TRANSL_CONT VARCHAR2(500),
-- 										MULTLANG_MOD_DTTM TIMESTAMP,
-- 										MULTLANG_TRANSL_CONT_ABBR VARCHAR2(100),
-- 										MULTLANG_ABBR_USE_YN CHAR(1),
-- 										MULTLANG_TYP VARCHAR2(100) NOT NULL,
-- 										RMK VARCHAR2(1000),
-- 										STS CHAR(1) DEFAULT 'C',
-- 										REGR_ID VARCHAR2(50),
-- 										REG_DTTM TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
-- 										MODR_ID VARCHAR2(50),
-- 										MOD_DTTM TIMESTAMP,
-- 										MULTLANG_TRANSL_FNL_CONT VARCHAR2(500)
-- );


