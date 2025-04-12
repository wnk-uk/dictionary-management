package com.emro.dictionary.users.dto;

import com.emro.dictionary.users.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String deptNm;
	private String usrNm;
	private Role role;
	private String token;
	private String useYn;
}
