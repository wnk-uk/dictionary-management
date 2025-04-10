package com.emro.dictionary.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

	public Long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
			throw new IllegalStateException("User not authenticated");
		}

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		return userDetails.getUserId();
	}

	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
			throw new IllegalStateException("User not authenticated");
		}

		return authentication.getName();
	}
}