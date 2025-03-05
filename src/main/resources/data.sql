INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES
     ('admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'ADMIN'),
     ('sys_admin', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'SYS_ADMIN'),
     ('user', '$2a$10$l/B5WXGGuIwZmcWZjEeNuuKpIwkqv9wEOj.u8a66Uxna1SSgC3S9S', 'USER');


INSERT INTO DIC_REQ (DIC_REQ_ID, REQ_USR_NM, REQ_DTTM) VALUES
    (999, 'admin', now());