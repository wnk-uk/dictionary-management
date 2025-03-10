INSERT INTO USERS (USERNAME, PASSWORD, ROLE, DEPT_NM, USR_NM) VALUES
     ('admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'ADMIN', 'D2S솔루션그룹', '다국어 관리자'),
     ('sys_admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'SYS_ADMIN', 'D2S솔루션그룹', '시스템 관리자'),
     ('user', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER', 'P2P솔루션그룹', '사용자');

-- MULTLANG 테이블 더미 데이터 삽입
INSERT INTO MULTLANG (multlang_ccd, multlang_key, multlang_transl_cont, multlang_mod_dttm, multlang_transl_cont_abbr,
                      multlang_abbr_use_yn, multlang_typ, rmk, sts, regr_id, reg_dttm, modr_id, mod_dttm, multlang_transl_fnl_cont)
VALUES
    ('EN', 'HELLO', 'Hello', CURRENT_TIMESTAMP, 'Hi', 'Y', 'GREETING', 'Standard greeting', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Hello'),
    ('EN', 'GOODBYE', 'Goodbye', CURRENT_TIMESTAMP, 'Bye', 'Y', 'GREETING', 'Farewell message', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Goodbye'),
    ('EN', 'THANKS', 'Thank you', CURRENT_TIMESTAMP, 'Thx', 'Y', 'POLITE', 'Expression of gratitude', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Thank you'),
    ('EN', 'YES', 'Yes', CURRENT_TIMESTAMP, 'Y', 'Y', 'CONFIRM', 'Affirmative response', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Yes'),
    ('EN', 'NO', 'No', CURRENT_TIMESTAMP, 'N', 'Y', 'CONFIRM', 'Negative response', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'No'),
    ('KR', 'HELLO', '안녕하세요', CURRENT_TIMESTAMP, '안녕', 'Y', 'GREETING', '한국어 인사', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '안녕하세요'),
    ('KR', 'GOODBYE', '안녕히 가세요', CURRENT_TIMESTAMP, '잘가', 'Y', 'GREETING', '이별 인사', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '안녕히 가세요'),
    ('KR', 'THANKS', '감사합니다', CURRENT_TIMESTAMP, '고맙', 'Y', 'POLITE', '감사의 표현', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '감사합니다'),
    ('KR', 'YES', '네', CURRENT_TIMESTAMP, 'ㅇㅇ', 'Y', 'CONFIRM', '긍정적인 대답', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '네'),
    ('KR', 'NO', '아니요', CURRENT_TIMESTAMP, 'ㄴㄴ', 'Y', 'CONFIRM', '부정적인 대답', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '아니요');

-- DIC_REQ 테이블 더미 데이터 삽입
-- DIC_REQ 테이블에 더미 데이터 삽입
INSERT INTO DIC_REQ (req_usr_nm, req_dttm, sts, acpt_sts)
VALUES
	('Alice', CURRENT_TIMESTAMP, 'C', 'REQ'),
	('Bob', CURRENT_TIMESTAMP, 'C', 'HOLD'),
	('Charlie', CURRENT_TIMESTAMP, 'C', 'ACPT'),
	('David', CURRENT_TIMESTAMP, 'C', 'DEL'),
	('Eve', CURRENT_TIMESTAMP, 'C', 'REQ'),
	('Frank', CURRENT_TIMESTAMP, 'C', 'HOLD'),
	('Grace', CURRENT_TIMESTAMP, 'C', 'ACPT'),
	('Hank', CURRENT_TIMESTAMP, 'C', 'DEL'),
	('Ivy', CURRENT_TIMESTAMP, 'C', 'REQ'),
	('Jack', CURRENT_TIMESTAMP, 'C', 'HOLD');

-- DIC_REQ_DTL 테이블 더미 데이터 삽입
INSERT INTO DIC_REQ_DTL (dic_req_id, existing_word, multlang_ccd, multlang_key, multlang_transl_cont, multlang_transl_cont_abbr, multlang_typ, screen_path, source_path, comment, reg_sts)
VALUES
	(1, 'HI', 'en_US', 'HELLO', 'Hello', 'HI', 'button', '/home', '/src/components/button.js', 'Greeting word', 'APPROVAL'),
	(1, NULL, 'en_US', 'GOODBYE', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Farewell message', 'REJECT'),
	(2, '안녕', 'ko_KR', '안녕하세요', 'Hello', '안녕', 'button', '/home', '/src/components/button.js', 'Korean greeting', 'APPROVAL'),
	(2, NULL, 'ko_KR', '잘 가', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Korean farewell', 'REJECT'),
	(3, NULL, 'en_US', 'THANKS', 'Thank you', 'THX', 'button', '/thanks', '/src/components/button.js', 'Expression of gratitude', 'APPROVAL'),
	(3, NULL, 'en_US', 'YES', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Confirmation response', 'REJECT'),
	(4, '고맙습니다', 'ko_KR', '고마워', 'Thanks', 'THX', 'button', '/thanks', '/src/components/button.js', 'Korean gratitude', 'APPROVAL'),
	(4, '그렇다', 'ko_KR', '네', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Korean confirmation', 'REJECT'),
	(5, NULL, 'en_US', 'NO', 'No', 'N', 'button', '/decline', '/src/components/button.js', 'Negative response', 'APPROVAL'),
	(5, NULL, 'ko_KR', '아니요', 'No', 'N', 'label', '/decline', '/src/components/label.js', 'Korean negative response', 'REJECT');

