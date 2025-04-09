CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
									 role VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ADMIN', 'SYS_ADMIN')),
                                     dept_nm VARCHAR(50),
                                     usr_nm VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS sheet_data (
                                     id BIGSERIAL PRIMARY KEY,
                                     json_data CLOB NOT NULL
);

CREATE TABLE IF NOT EXISTS req (
                                    req_id BIGSERIAL PRIMARY KEY,
                                    req_usr_nm VARCHAR(50),
                                    req_dttm TIMESTAMP,
                                    sts CHAR(1) DEFAULT 'C',
									image_path TEXT,
									editor_content TEXT,
                                    acpt_sts VARCHAR(50) -- REQUEST, PROGRESS, HOLDING, COMPLETE
);

CREATE TABLE IF NOT EXISTS req_dtl (
                                    dtl_id BIGSERIAL PRIMARY KEY, -- 상세 요청 ID (PK)
                                    req_id INT,
									existing_word VARCHAR(500), -- 기존 단어
                                    multlang_ccd VARCHAR(50) NOT NULL, -- 언어 코드 ko_KR, en_US, ja_JP, zh_CN
                                    multlang_key VARCHAR(500) NOT NULL, -- 등록할 단어
                                    multlang_suggested_transl_cont VARCHAR(500), -- 사용자 제안 번역
                                    multlang_transl_cont VARCHAR(500), -- 등록할 번역
                                    multlang_transl_cont_abbr VARCHAR(100), -- 등록할 약어
                                    multlang_typ VARCHAR(100) NOT NULL, -- 등록 유형 button, label
                                    screen_path VARCHAR(255), -- 화면 경로
                                    source_path VARCHAR(255), -- 소스 코드 경로
                                    comment VARCHAR(1000), -- 요청자의 코멘트
                                    reg_sts VARCHAR(50) DEFAULT 'PENDING', -- 등록 상태 (PENDING, ACCEPTANCE, REJECT, HOLDING)
                                    FOREIGN KEY (req_id) REFERENCES req(req_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS req_dtl_his (
                                    dtl_his_id BIGSERIAL PRIMARY KEY,
                                    dtl_id BIGINT NOT NULL,
                                    comment_text TEXT,
                                    image_path TEXT,
									writer_nm VARCHAR(50) NOT NULL,
									written_dttm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									FOREIGN KEY (dtl_id) REFERENCES req_dtl(dtl_id) ON DELETE CASCADE
	);

CREATE TABLE IF NOT EXISTS multlang (
									multlang_id BIGSERIAL PRIMARY KEY,
	                                multlang_ccd VARCHAR(50) NOT NULL, --언어 코드 ko_KR, en_US, ja_JP, zh_CN
									multlang_key VARCHAR(500) NOT NULL, -- 입력 단어, 내용
									multlang_transl_cont VARCHAR(500), -- 단어, 내용에 대한 출력
									multlang_mod_dttm TIMESTAMP, -- 등록시간
									multlang_transl_cont_abbr VARCHAR(100), -- 약어
									multlang_abbr_use_yn CHAR(1), -- 약어 사용 여부
									multlang_typ VARCHAR(100) NOT NULL, -- 등록 유형 button, label
									rmk VARCHAR(1000),
									sts CHAR(1) DEFAULT 'C',
									regr_id VARCHAR(50),
									reg_dttm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
									modr_id VARCHAR(50),
									mod_dttm TIMESTAMP,
									multlang_transl_fnl_cont VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS temp_table (
								   id BIGSERIAL PRIMARY KEY,
			                       category VARCHAR(50),
			                       source_path VARCHAR(255),
			                       screen_path VARCHAR(255),
			                       korean VARCHAR(255),
			                       korean_modified VARCHAR(255),
			                       english VARCHAR(255),
			                       english_modified VARCHAR(255),
			                       regulation_type VARCHAR(50),
			                       symbol_protein VARCHAR(100),
			                       paid_user VARCHAR(100),
			                       reviewer VARCHAR(100),
								   requester VARCHAR(100),
								   inspector VARCHAR(100),
								   requester_aggregated VARCHAR(100),
);

