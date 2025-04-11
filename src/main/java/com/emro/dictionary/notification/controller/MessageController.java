package com.emro.dictionary.notification.controller;

import com.emro.dictionary.notification.dto.MessageDTO;
import com.emro.dictionary.notification.service.MessageService;
import com.emro.dictionary.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
	private final MessageService messageService;
	private final SecurityUtil securityUtil;

	@GetMapping("")
	public List<MessageDTO> getMyMessages() {
		Long userId = securityUtil.getUserId();
		return messageService.getMessagesByUserId(userId);
	}

	@PostMapping("/{id}/read")
	public ResponseEntity<?> readMessage(@PathVariable Long id) {
		messageService.updateMessageIsRead(id);
		return ResponseEntity.ok("✅ Status Updated successfully");
	}
}