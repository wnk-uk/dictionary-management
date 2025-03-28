package com.emro.dictionary.users.entity;

import com.emro.dictionary.users.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")  // 🔹 password 필드 제외
public class User {
    private Long id;
    private String username;
    private String password;
    private String deptNm;
    private String usrNm;
    private Role role;
}

