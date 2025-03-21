package com.emro.dictionary.request.service.resolver;

import java.util.Arrays;

/**
 * 요청 서비스 역할을 정의하는 Enum
 */
public enum RequestServiceRole {
	USER("ROLE_USER", "userRequestService"),
	ADMIN("ROLE_ADMIN", "adminRequestService"),
	SYS_ADMIN("ROLE_SYS_ADMIN", "sysAdminRequestService");

	private final String role;
	private final String serviceBeanName;

	RequestServiceRole(String role, String serviceBeanName) {
		this.role = role;
		this.serviceBeanName = serviceBeanName;
	}

	public String getRole() {
		return role;
	}

	public String getServiceBeanName() {
		return serviceBeanName;
	}

	/**
	 * 주어진 권한에 맞는 역할 찾기
	 * @param authority 사용자 권한
	 * @return 해당 역할 (기본값: USER)
	 */
	public static RequestServiceRole fromAuthority(String authority) {
		return Arrays.stream(values())
				.filter(role -> role.getRole().equals(authority))
				.findFirst()
				.orElse(USER); // 기본값으로 USER 반환
	}
}