package com.emro.dictionary.users.entity;

import com.emro.dictionary.users.enums.Role;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")  // 🔹 password 필드 제외
public class SignUpForm {
    private String userId;
    private String userName;
    private String password;
    private String deptCd;
    private String deptNm;
    private Role role = Role.USER;
}
