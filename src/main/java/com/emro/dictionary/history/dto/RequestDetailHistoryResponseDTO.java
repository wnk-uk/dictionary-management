package com.emro.dictionary.history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDetailHistoryResponseDTO {
	private Long dtlHisId;
	private Long dtlId;
	private String commentText;
	private String imagePath;
	private String writerNm;
	private LocalDateTime writtenDttm;
}
