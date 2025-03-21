package com.emro.dictionary.request.service.resolver;

import com.emro.dictionary.request.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 역할별 RequestService를 해결하는 유틸리티 클래스
 */
@Component
public class RequestServiceResolver {

	private final Map<String, RequestService> requestServiceMap;

	public RequestServiceResolver(Map<String, RequestService> requestServiceMap) {
		this.requestServiceMap = requestServiceMap;
	}

	/**
	 * 현재 사용자의 역할에 맞는 서비스 반환
	 * @return RequestService 구현체
	 */
	public RequestService getService() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authority = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority())
				.findFirst()
				.orElse("ROLE_USER");
		return requestServiceMap.get(RequestServiceRole.fromAuthority(authority).getServiceBeanName());
	}

	/**
	 * 현재 사용자의 이름 반환
	 * @return 사용자 이름
	 */
	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}