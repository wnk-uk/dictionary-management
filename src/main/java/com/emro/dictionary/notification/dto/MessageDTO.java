package com.emro.dictionary.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
	private Long id;
	private Long userId;
	private Long reqId;
	private Long reqDtlId;
	private Long reqDtlHisId;
	private String message;
	private boolean isRead;
	private LocalDateTime createdAt;
}
