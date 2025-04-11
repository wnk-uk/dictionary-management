package com.emro.dictionary.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
	private Long id;
	private Long userId;
	private Long reqId;
	private Long dtlId;
	private Long dtlHisId;
	private String message;
	private boolean isRead;
	private LocalDateTime createdAt;
}
