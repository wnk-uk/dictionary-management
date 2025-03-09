INSERT INTO USERS (USERNAME, PASSWORD, ROLE, DEPT_NM, USR_NM) VALUES
     ('admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'ADMIN', 'D2S솔루션그룹', '다국어 관리자'),
     ('sys_admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'SYS_ADMIN', 'D2S솔루션그룹', '시스템 관리자'),
     ('user', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER', 'P2P솔루션그룹', '사용자');


INSERT INTO DIC_REQ (DIC_REQ_ID, REQ_USR_NM, REQ_DTTM) VALUES
    (999, 'admin', now());