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
INSERT INTO DIC_REQ_DTL (dic_req_id, multlang_ccd, multlang_key, reg_sts)
VALUES
    (1, 'EN', 'HELLO', 'APPROVAL'),
    (1, 'EN', 'GOODBYE', 'REJECT'),
    (2, 'KR', 'HELLO', 'APPROVAL'),
    (2, 'KR', 'GOODBYE', 'REJECT'),
    (3, 'EN', 'THANKS', 'APPROVAL'),
    (3, 'EN', 'YES', 'REJECT'),
    (4, 'KR', 'THANKS', 'APPROVAL'),
    (4, 'KR', 'YES', 'REJECT'),
    (5, 'EN', 'NO', 'APPROVAL'),
    (5, 'KR', 'NO', 'REJECT');
