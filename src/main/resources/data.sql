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

-- DIC_REQ 테이블에 더미 데이터 삽입
INSERT INTO DIC_REQ (req_usr_nm, req_dttm, sts, acpt_sts)
VALUES
	('Alice', '2024-03-01 08:15:30', 'C', 'REQ'),
	('Bob', '2024-03-01 09:25:45', 'C', 'HOLD'),
	('Charlie', '2024-03-01 10:35:20', 'C', 'ACPT'),
	('David', '2024-03-01 11:45:10', 'C', 'PROG'),
	('Eve', '2024-03-02 12:55:05', 'C', 'REQ'),
	('Frank', '2024-03-02 14:05:30', 'C', 'HOLD'),
	('Grace', '2024-03-02 15:15:55', 'C', 'ACPT'),
	('Hank', '2024-03-02 16:25:40', 'C', 'PROG'),
	('Ivy', '2024-03-03 17:35:20', 'C', 'REQ'),
	('Jack', '2024-03-03 18:45:10', 'C', 'HOLD'),
	('Kelly', '2024-03-03 19:55:05', 'C', 'REQ'),
	('Leo', '2024-03-04 07:10:30', 'C', 'HOLD'),
	('Mia', '2024-03-04 08:20:45', 'C', 'ACPT'),
	('Nathan', '2024-03-04 09:30:15', 'C', 'PROG'),
	('Olivia', '2024-03-04 10:40:50', 'C', 'REQ'),
	('Peter', '2024-03-05 11:50:25', 'C', 'HOLD'),
	('Quinn', '2024-03-05 13:00:30', 'C', 'ACPT'),
	('Rachel', '2024-03-05 14:10:55', 'C', 'PROG'),
	('Steve', '2024-03-05 15:20:40', 'C', 'REQ'),
	('Tina', '2024-03-06 16:30:25', 'C', 'HOLD'),
	('Uma', '2024-03-06 17:40:55', 'C', 'REQ'),
	('Victor', '2024-03-06 18:50:10', 'C', 'HOLD'),
	('Wendy', '2024-03-07 06:05:30', 'C', 'ACPT'),
	('Xander', '2024-03-07 07:15:45', 'C', 'PROG'),
	('Yara', '2024-03-07 08:25:55', 'C', 'REQ'),
	('Zack', '2024-03-07 09:35:40', 'C', 'HOLD'),
	('Aaron', '2024-03-08 10:45:30', 'C', 'ACPT'),
	('Bella', '2024-03-08 11:55:20', 'C', 'PROG'),
	('Chris', '2024-03-08 13:05:10', 'C', 'REQ'),
	('Diana', '2024-03-08 14:15:00', 'C', 'HOLD');


-- DIC_REQ_DTL 테이블 더미 데이터 삽입
INSERT INTO DIC_REQ_DTL (dic_req_id, existing_word, multlang_ccd, multlang_key, multlang_transl_cont, multlang_transl_cont_abbr, multlang_typ, screen_path, source_path, comment, reg_sts)
VALUES
	(1, 'HI', 'en_US', 'HELLO', 'Hello', 'HI', 'button', '/home', '/src/components/button.js', 'Greeting update', 'COMPLETE'),
	(1, 'BYE', 'en_US', 'GOODBYE', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Farewell update', 'HOLDING'),
	(2, '안녕', 'ko_KR', '안녕하세요', 'Hello', '안녕', 'button', '/home', '/src/components/button.js', 'Formal greeting update', 'COMPLETE'),
	(2, null, 'ko_KR', '잘 가세요', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Polite farewell term update', 'HOLDING'),
	(3, 'THX', 'en_US', 'THANKS', 'Thank you', 'THX', 'button', '/thanks', '/src/components/button.js', 'Expression of gratitude update', 'HOLDING'),
	(3, null, 'en_US', 'YES', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Confirmation message update', 'PENDING'),
	(4, '고맙습니다', 'ko_KR', '고마워요', 'Thanks', 'THX', 'button', '/thanks', '/src/components/button.js', 'Polite gratitude update', 'HOLDING'),
	(4, '그렇다', 'ko_KR', '네', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Korean affirmation update', 'PENDING'),
	(5, 'N', 'en_US', 'NO', 'No', 'N', 'button', '/decline', '/src/components/button.js', 'Negative response update', 'COMPLETE'),
	(5, '아니', 'ko_KR', '아니요', 'No', 'N', 'label', '/decline', '/src/components/label.js', 'Formal negative response update', 'PENDING'),
	(6, 'OK', 'en_US', 'OK', 'Okay', 'OK', 'button', '/accept', '/src/components/button.js', 'Approval term update', 'COMPLETE'),
	(6, 'CANCEL', 'en_US', 'CANCEL', 'Cancel', 'CXL', 'label', '/cancel', '/src/components/label.js', 'Cancel button update', 'HOLDING'),
	(7, 'SUBMIT', 'en_US', 'SUBMIT', 'Submit', 'SUB', 'button', '/submit', '/src/components/button.js', 'Submission term update', 'PENDING'),
	(7, 'RESET', 'en_US', 'RESET', 'Reset', 'RST', 'label', '/reset', '/src/components/label.js', 'Reset button update', 'HOLDING'),
	(8, 'START', 'en_US', 'START', 'Start', 'STR', 'button', '/start', '/src/components/button.js', 'Start action update', 'COMPLETE'),
	(8, null, 'en_US', 'STOP', 'Stop', 'STP', 'label', '/stop', '/src/components/label.js', 'Stop action update', 'PENDING'),
	(9, 'OPEN', 'en_US', 'OPEN', 'Open', 'OPN', 'button', '/open', '/src/components/button.js', 'Open action update', 'HOLDING'),
	(9, 'CLOSE', 'en_US', 'CLOSE', 'Close', 'CLS', 'label', '/close', '/src/components/label.js', 'Close action update', 'PENDING'),
	(10, 'SAVE', 'en_US', 'SAVE', 'Save', 'SAV', 'button', '/save', '/src/components/button.js', 'Save action update', 'COMPLETE'),
	(10, 'DELETE', 'en_US', 'DELETE', 'Delete', 'DEL', 'label', '/delete', '/src/components/label.js', 'Delete action update', 'HOLDING'),
	(11, null, 'en_US', 'UP', 'Up', 'UP', 'button', '/move', '/src/components/button.js', 'Move up action update', 'PENDING'),
	(11, null, 'en_US', 'DOWN', 'Down', 'DWN', 'label', '/move', '/src/components/label.js', 'Move down action update', 'COMPLETE'),
	(12, 'YES', 'en_US', 'YES', 'Yes', 'Y', 'button', '/confirm', '/src/components/button.js', 'Confirmation term update', 'HOLDING'),
	(12, 'NO', 'en_US', 'NO', 'No', 'N', 'label', '/decline', '/src/components/label.js', 'Negative response update', 'PENDING'),
	(13, 'NEXT', 'en_US', 'NEXT', 'Next', 'NXT', 'button', '/navigation', '/src/components/button.js', 'Next step update', 'COMPLETE'),
	(13, 'PREVIOUS', 'en_US', 'PREVIOUS', 'Previous', 'PRV', 'label', '/navigation', '/src/components/label.js', 'Previous step update', 'HOLDING'),
	(14, null, 'en_US', 'EXIT', 'Exit', 'EXT', 'button', '/exit', '/src/components/button.js', 'Exit button update', 'PENDING'),
	(14, 'ENTER', 'en_US', 'ENTER', 'Enter', 'ENT', 'label', '/enter', '/src/components/label.js', 'Enter button update', 'COMPLETE');
