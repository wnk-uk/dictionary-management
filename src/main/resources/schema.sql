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
                                    req_user_id BIGINT NOT NULL,
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
									writer_id BIGINT NOT NULL,
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
								   requester_aggregated VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS notifications (
									id BIGSERIAL PRIMARY KEY,
									user_id BIGINT NOT NULL, -- 알림을 받는 유저/어드민 (users.id 참조)
									req_id BIGINT NOT NULL, -- 관련된 요청 (req.req_id 참조)
									req_dtl_id BIGINT, -- 관련된 요청 상세 (req_dtl.dtl_id 참조, req_dtl 상태 변경 시 사용)
									type VARCHAR(50) NOT NULL CHECK (type IN ('REQUEST_CREATED', 'STATUS_CHANGED', 'REQ_DTL_STATUS_CHANGED')), -- 알림 유형
									message TEXT NOT NULL, -- 알림 메시지 내용
									is_read BOOLEAN DEFAULT FALSE, -- 읽음 여부
									created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
									FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
									FOREIGN KEY (req_id) REFERENCES req(req_id) ON DELETE CASCADE,
									FOREIGN KEY (req_dtl_id) REFERENCES req_dtl(dtl_id) ON DELETE CASCADE
	);


CREATE TABLE IF NOT EXISTS messages (
									id BIGSERIAL PRIMARY KEY,
									user_id BIGINT NOT NULL, -- 메시지를 받는 유저/어드민 (users.id 참조)
									req_id BIGINT NOT NULL, -- 관련된 요청 (req.req_id 참조)
									req_dtl_id BIGINT NOT NULL, -- 관련된 요청 상세 (req_dtl.dtl_id 참조)
									req_dtl_his_id BIGINT NOT NULL, -- 관련된 댓글 (req_dtl_his.dtl_his_id 참조)
									message TEXT NOT NULL, -- 메시지 내용
									is_read BOOLEAN DEFAULT FALSE, -- 읽음 여부
									created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
									FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
									FOREIGN KEY (req_id) REFERENCES req(req_id) ON DELETE CASCADE,
									FOREIGN KEY (req_dtl_id) REFERENCES req_dtl(dtl_id) ON DELETE CASCADE,
									FOREIGN KEY (req_dtl_his_id) REFERENCES req_dtl_his(dtl_his_id) ON DELETE CASCADE
	);
