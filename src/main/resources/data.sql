INSERT INTO USERS (USERNAME, PASSWORD, ROLE, DEPT_NM, USR_NM) VALUES
     ('admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'ADMIN', 'D2S솔루션그룹', '다국어 관리자'),
     ('sys_admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'SYS_ADMIN', 'D2S솔루션그룹', '시스템 관리자'),
     ('user', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER', 'P2P솔루션그룹', '사용자'),
	('alice',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      'AI기술그룹',         '앨리스'),
	('bob',        '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      '플랫폼개발팀',       '밥'),
	('charlie',    '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      '웹개발팀',           '찰리'),
	('david',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      '모바일개발팀',       '데이비드'),
	('eva',        '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      'QA팀',               '에바'),
	('frank',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',     '플랫폼기획팀',       '프랭크'),
	('grace',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',     'UX디자인팀',         '그레이스'),
	('henry',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER', '보안팀',             '헨리'),
	('irene',      '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER', '인프라팀',           '아이린'),
	('jackson',    '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER',      '데이터분석팀',       '잭슨');

-- MULTLANG 테이블 더미 데이터 삽입
INSERT INTO MULTLANG (multlang_ccd, multlang_key, multlang_transl_cont, multlang_mod_dttm, multlang_transl_cont_abbr,
                      multlang_abbr_use_yn, multlang_typ, rmk, sts, regr_id, reg_dttm, modr_id, mod_dttm, multlang_transl_fnl_cont)
VALUES
    ('en_US', 'HELLO', 'Hello', CURRENT_TIMESTAMP, 'Hi', 'Y', 'label', 'Standard greeting', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Hello'),
    ('en_US', 'GOODBYE', 'Goodbye', CURRENT_TIMESTAMP, 'Bye', 'Y', 'label', 'Farewell message', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Goodbye'),
    ('en_US', 'THANKS', 'Thank you', CURRENT_TIMESTAMP, 'Thx', 'Y', 'label', 'Expression of gratitude', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Thank you'),
    ('en_US', 'YES', 'Yes', CURRENT_TIMESTAMP, 'Y', 'Y', 'label', 'Affirmative response', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'Yes'),
    ('en_US', 'NO', 'No', CURRENT_TIMESTAMP, 'N', 'Y', 'label', 'Negative response', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, 'No'),
    ('ko_KR', 'HELLO', '안녕하세요', CURRENT_TIMESTAMP, '안녕', 'Y', 'label', '한국어 인사', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '안녕하세요'),
    ('ko_KR', 'GOODBYE', '안녕히 가세요', CURRENT_TIMESTAMP, '잘가', 'Y', 'label', '이별 인사', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '안녕히 가세요'),
    ('ko_KR', 'THANKS', '감사합니다', CURRENT_TIMESTAMP, '고맙', 'Y', 'label', '감사의 표현', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '감사합니다'),
    ('ko_KR', 'YES', '네', CURRENT_TIMESTAMP, 'ㅇㅇ', 'Y', 'button', '긍정적인 대답', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '네'),
    ('ko_KR', 'NO', '아니요', CURRENT_TIMESTAMP, 'ㄴㄴ', 'Y', 'button', '부정적인 대답', 'C', 'admin', CURRENT_TIMESTAMP, NULL, NULL, '아니요');

-- REQ 테이블에 더미 데이터 삽입
INSERT INTO REQ (req_user_id, req_dttm, sts, acpt_sts)
VALUES
	('1', '2024-03-01 08:15:30', 'C', 'REQUEST'),
	('2', '2024-03-01 09:25:45', 'C', 'HOLDING'),
	('3', '2024-03-01 10:35:20', 'C', 'COMPLETE'),
	('4', '2024-03-01 11:45:10', 'C', 'PROGRESS'),
	('5', '2024-03-02 12:55:05', 'C', 'REQUEST'),
	('6', '2024-03-02 14:05:30', 'C', 'HOLDING'),
	('7', '2024-03-02 15:15:55', 'C', 'COMPLETE'),
	('8', '2024-03-02 16:25:40', 'C', 'PROGRESS'),
	('9', '2024-03-03 17:35:20', 'C', 'REQUEST'),
	('3', '2024-03-03 18:45:10', 'C', 'HOLDING'),
	('3', '2024-03-03 19:55:05', 'C', 'REQUEST'),
	('3', '2024-03-04 07:10:30', 'C', 'HOLDING'),
	('3', '2024-03-04 08:20:45', 'C', 'COMPLETE'),
	('3', '2024-03-04 09:30:15', 'C', 'PROGRESS'),
	('4', '2024-03-04 10:40:50', 'C', 'REQUEST'),
	('5', '2024-03-05 11:50:25', 'C', 'HOLDING'),
	('6', '2024-03-05 13:00:30', 'C', 'COMPLETE'),
	('7', '2024-03-05 14:10:55', 'C', 'PROGRESS'),
	('8', '2024-03-05 15:20:40', 'C', 'REQUEST'),
	('9', '2024-03-06 16:30:25', 'C', 'HOLDING'),
	('10', '2024-03-06 17:40:55', 'C', 'REQUEST'),
	('11', '2024-03-06 18:50:10', 'C', 'HOLDING'),
	('3', '2024-03-07 06:05:30', 'C', 'COMPLETE'),
	('4', '2024-03-07 07:15:45', 'C', 'PROGRESS'),
	('3', '2024-03-07 08:25:55', 'C', 'REQUEST'),
	('5', '2024-03-07 09:35:40', 'C', 'HOLDING'),
	('3', '2024-03-08 10:45:30', 'C', 'COMPLETE'),
	('6', '2024-03-08 11:55:20', 'C', 'PROGRESS'),
	('3', '2024-03-08 13:05:10', 'C', 'REQUEST'),
	('7', '2024-03-08 14:15:00', 'C', 'HOLDING');


-- REQ_DTL 테이블 더미 데이터 삽입
INSERT INTO REQ_DTL (req_id, existing_word, multlang_ccd, multlang_key, multlang_suggested_transl_cont, multlang_transl_cont_abbr, multlang_typ, screen_path, source_path, comment, reg_sts)
VALUES
	(1, 'HI', 'en_US', 'HELLO', 'Hello', 'HI', 'button', '/home', '/src/components/button.js', 'Greeting update', 'ACCEPTANCE'),
	(1, 'BYE', 'en_US', 'GOODBYE', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Farewell update', 'HOLDING'),
	(2, '안녕', 'ko_KR', '안녕하세요', 'Hello', '안녕', 'button', '/home', '/src/components/button.js', 'Formal greeting update', 'ACCEPTANCE'),
	(2, null, 'ko_KR', '잘 가세요', 'Goodbye', 'BYE', 'label', '/exit', '/src/components/label.js', 'Polite farewell term update', 'HOLDING'),
	(3, 'THX', 'en_US', 'THANKS', 'Thank you', 'THX', 'button', '/thanks', '/src/components/button.js', 'Expression of gratitude update', 'HOLDING'),
	(3, null, 'en_US', 'YES', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Confirmation message update', 'PENDING'),
	(4, '고맙습니다', 'ko_KR', '고마워요', 'Thanks', 'THX', 'button', '/thanks', '/src/components/button.js', 'Polite gratitude update', 'HOLDING'),
	(4, '그렇다', 'ko_KR', '네', 'Yes', 'Y', 'label', '/confirm', '/src/components/label.js', 'Korean affirmation update', 'PENDING'),
	(5, 'N', 'en_US', 'NO', 'No', 'N', 'button', '/decline', '/src/components/button.js', 'Negative response update', 'ACCEPTANCE'),
	(5, '아니', 'ko_KR', '아니요', 'No', 'N', 'label', '/decline', '/src/components/label.js', 'Formal negative response update', 'PENDING'),
	(6, 'OK', 'en_US', 'OK', 'Okay', 'OK', 'button', '/accept', '/src/components/button.js', 'Approval term update', 'ACCEPTANCE'),
	(6, 'CANCEL', 'en_US', 'CANCEL', 'Cancel', 'CXL', 'label', '/cancel', '/src/components/label.js', 'Cancel button update', 'HOLDING'),
	(7, 'SUBMIT', 'en_US', 'SUBMIT', 'Submit', 'SUB', 'button', '/submit', '/src/components/button.js', 'Submission term update', 'PENDING'),
	(7, 'RESET', 'en_US', 'RESET', 'Reset', 'RST', 'label', '/reset', '/src/components/label.js', 'Reset button update', 'HOLDING'),
	(8, 'START', 'en_US', 'START', 'Start', 'STR', 'button', '/start', '/src/components/button.js', 'Start action update', 'ACCEPTANCE'),
	(8, null, 'en_US', 'STOP', 'Stop', 'STP', 'label', '/stop', '/src/components/label.js', 'Stop action update', 'PENDING'),
	(9, 'OPEN', 'en_US', 'OPEN', 'Open', 'OPN', 'button', '/open', '/src/components/button.js', 'Open action update', 'HOLDING'),
	(9, 'CLOSE', 'en_US', 'CLOSE', 'Close', 'CLS', 'label', '/close', '/src/components/label.js', 'Close action update', 'PENDING'),
	(10, 'SAVE', 'en_US', 'SAVE', 'Save', 'SAV', 'button', '/save', '/src/components/button.js', 'Save action update', 'ACCEPTANCE'),
	(10, 'DELETE', 'en_US', 'DELETE', 'Delete', 'DEL', 'label', '/delete', '/src/components/label.js', 'Delete action update', 'HOLDING'),
	(11, null, 'en_US', 'UP', 'Up', 'UP', 'button', '/move', '/src/components/button.js', 'Move up action update', 'PENDING'),
	(11, null, 'en_US', 'DOWN', 'Down', 'DWN', 'label', '/move', '/src/components/label.js', 'Move down action update', 'ACCEPTANCE'),
	(12, 'YES', 'en_US', 'YES', 'Yes', 'Y', 'button', '/confirm', '/src/components/button.js', 'Confirmation term update', 'HOLDING'),
	(12, 'NO', 'en_US', 'NO', 'No', 'N', 'label', '/decline', '/src/components/label.js', 'Negative response update', 'PENDING'),
	(13, 'NEXT', 'en_US', 'NEXT', 'Next', 'NXT', 'button', '/navigation', '/src/components/button.js', 'Next step update', 'ACCEPTANCE'),
	(13, 'PREVIOUS', 'en_US', 'PREVIOUS', 'Previous', 'PRV', 'label', '/navigation', '/src/components/label.js', 'Previous step update', 'HOLDING'),
	(14, null, 'en_US', 'EXIT', 'Exit', 'EXT', 'button', '/exit', '/src/components/button.js', 'Exit button update', 'PENDING'),
	(14, 'ENTER', 'en_US', 'ENTER', 'Enter', 'ENT', 'label', '/enter', '/src/components/label.js', 'Enter button update', 'ACCEPTANCE'),
	(15, 'WELCOME', 'en_US', 'WELCOME', 'Welcome', 'WLC', 'button', '/home', '/src/components/button.js', 'Welcome message update', 'ACCEPTANCE'),
    (15, 'WELCOME', 'ko_KR', 'WELCOME', '환영합니다', '환영', 'label', '/home', '/src/components/label.js', 'Korean welcome update', 'HOLDING'),
    (16, 'LOGOUT', 'en_US', 'LOGOUT', 'Logout', 'LGT', 'button', '/logout', '/src/components/button.js', 'Logout button update', 'PENDING'),
    (16, 'LOGOUT', 'ko_KR', 'LOGOUT', '로그아웃', '로그아웃', 'label', '/logout', '/src/components/label.js', 'Korean logout update', 'ACCEPTANCE'),
    (17, 'HELP', 'en_US', 'HELP', 'Help', 'HLP', 'button', '/help', '/src/components/button.js', 'Help button update', 'HOLDING'),
    (17, 'HELP', 'ko_KR', 'HELP', '도움말', '도움', 'label', '/help', '/src/components/label.js', 'Korean help update', 'PENDING'),
    (18, 'SETTINGS', 'en_US', 'SETTINGS', 'Settings', 'SET', 'button', '/settings', '/src/components/button.js', 'Settings update', 'ACCEPTANCE'),
    (18, 'SETTINGS', 'ko_KR', 'SETTINGS', '설정', '설정', 'label', '/settings', '/src/components/label.js', 'Korean settings update', 'HOLDING'),
    (19, 'PROFILE', 'en_US', 'PROFILE', 'Profile', 'PRF', 'button', '/profile', '/src/components/button.js', 'Profile update', 'PENDING'),
    (19, 'PROFILE', 'ko_KR', 'PROFILE', '프로필', '프로필', 'label', '/profile', '/src/components/label.js', 'Korean profile update', 'ACCEPTANCE'),
    (20, 'UPLOAD', 'en_US', 'UPLOAD', 'Upload', 'UPL', 'button', '/upload', '/src/components/button.js', 'Upload button update', 'HOLDING'),
    (20, 'UPLOAD', 'ko_KR', 'UPLOAD', '업로드', '업로드', 'label', '/upload', '/src/components/label.js', 'Korean upload update', 'PENDING'),
    (21, 'DOWNLOAD', 'en_US', 'DOWNLOAD', 'Download', 'DWL', 'button', '/download', '/src/components/button.js', 'Download button update', 'ACCEPTANCE'),
    (21, 'DOWNLOAD', 'ko_KR', 'DOWNLOAD', '다운로드', '다운로드', 'label', '/download', '/src/components/label.js', 'Korean download update', 'HOLDING'),
    (22, 'SEARCH', 'en_US', 'SEARCH', 'Search', 'SRCH', 'button', '/search', '/src/components/button.js', 'Search button update', 'PENDING'),
    (22, 'SEARCH', 'ko_KR', 'SEARCH', '검색', '검색', 'label', '/search', '/src/components/label.js', 'Korean search update', 'ACCEPTANCE'),
    (23, 'HOME', 'en_US', 'HOME', 'Home', 'HM', 'button', '/home', '/src/components/button.js', 'Home button update', 'HOLDING'),
    (23, 'HOME', 'ko_KR', 'HOME', '홈', '홈', 'label', '/home', '/src/components/label.js', 'Korean home update', 'PENDING'),
    (24, 'INFO', 'en_US', 'INFO', 'Information', 'INFO', 'button', '/info', '/src/components/button.js', 'Info page update', 'ACCEPTANCE'),
    (24, 'INFO', 'ko_KR', 'INFO', '정보', '정보', 'label', '/info', '/src/components/label.js', 'Korean info update', 'HOLDING'),
    (25, 'ERROR', 'en_US', 'ERROR', 'Error', 'ERR', 'button', '/error', '/src/components/button.js', 'Error message update', 'PENDING'),
    (25, 'ERROR', 'ko_KR', 'ERROR', '오류', '오류', 'label', '/error', '/src/components/label.js', 'Korean error update', 'ACCEPTANCE'),
    (26, 'RETRY', 'en_US', 'RETRY', 'Retry', 'RTY', 'button', '/retry', '/src/components/button.js', 'Retry action update', 'HOLDING'),
    (26, 'RETRY', 'ko_KR', 'RETRY', '재시도', '재시도', 'label', '/retry', '/src/components/label.js', 'Korean retry update', 'PENDING'),
    (27, 'CONFIRM', 'en_US', 'CONFIRM', 'Confirm', 'CFM', 'button', '/confirm', '/src/components/button.js', 'Confirm button update', 'ACCEPTANCE'),
    (27, 'CONFIRM', 'ko_KR', 'CONFIRM', '확인', '확인', 'label', '/confirm', '/src/components/label.js', 'Korean confirm update', 'HOLDING'),
    (28, 'CANCEL', 'en_US', 'CANCEL', 'Cancel', 'CXL', 'button', '/cancel', '/src/components/button.js', 'Cancel button update', 'PENDING'),
    (28, 'CANCEL', 'ko_KR', 'CANCEL', '취소', '취소', 'label', '/cancel', '/src/components/label.js', 'Korean cancel update', 'ACCEPTANCE'),
    (29, 'SEND', 'en_US', 'SEND', 'Send', 'SND', 'button', '/send', '/src/components/button.js', 'Send button update', 'HOLDING'),
    (29, 'SEND', 'ko_KR', 'SEND', '보내기', '보내기', 'label', '/send', '/src/components/label.js', 'Korean send update', 'PENDING'),
    (30, 'RECEIVE', 'en_US', 'RECEIVE', 'Receive', 'RCV', 'button', '/receive', '/src/components/button.js', 'Receive button update', 'ACCEPTANCE'),
    (30, 'RECEIVE', 'ko_KR', 'RECEIVE', '받기', '받기', 'label', '/receive', '/src/components/label.js', 'Korean receive update', 'HOLDING');


INSERT INTO notifications (user_id, req_id, dtl_id, type, message, is_read, created_at)
VALUES (2, 1, NULL, 'REQUEST_CREATED', '새로운 요청이 등록되었습니다.<br>(요청 ID: 1)', false, NOW());
