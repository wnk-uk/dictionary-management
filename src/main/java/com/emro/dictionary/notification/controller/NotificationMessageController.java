package com.emro.dictionary.notification.controller;

import com.emro.dictionary.notification.dto.MessageDTO;
import com.emro.dictionary.notification.dto.NotificationDTO;
import com.emro.dictionary.notification.service.MessageService;
import com.emro.dictionary.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationMessageController {
	private final NotificationService notificationService;
	private final MessageService messageService;


	@GetMapping("/notifications")
	public List<NotificationDTO> getMyNotifications(@RequestParam Long userId) {
		return notificationService.getNotificationsByUserId(userId);
	}

	@GetMapping("/messages")
	public List<MessageDTO> getMyMessages(@RequestParam Long userId) {
		return messageService.getMessagesByUserId(userId);
	}
}