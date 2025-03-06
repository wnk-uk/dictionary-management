package com.emro.dictionary.users;

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
    private String dept;
    private Role role;
}

