package com.emro.dictionary.notification.controller;

import com.emro.dictionary.notification.dto.NotificationDTO;
import com.emro.dictionary.notification.service.NotificationService;
import com.emro.dictionary.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
	private final NotificationService notificationService;
	private final SecurityUtil securityUtil;

	@GetMapping("")
	public ResponseEntity<List<NotificationDTO>> getMyNotifications() {
		Long userId = securityUtil.getUserId();
		return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
	}

	@PostMapping("/{id}/read")
	public ResponseEntity<?> readMessage(@PathVariable Long id) {
		notificationService.updateNotificationIsRead(id);
		return ResponseEntity.ok("✅ Status Updated successfully");
	}
}