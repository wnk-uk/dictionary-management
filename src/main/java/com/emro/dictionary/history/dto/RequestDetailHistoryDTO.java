package com.emro.dictionary.history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDetailHistoryDTO {
	private Long dtlHisId;
	private Long dtlId;
	private String commentText;
	private String imagePath;
	private Long writerId;
	private LocalDateTime writtenDttm;
}
