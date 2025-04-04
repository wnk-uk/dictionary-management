package com.emro.dictionary.history.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class RequestDetailHistoryDTO {
	private Long historyId;
	private Long dtlId;
	private String commentText;
	private String imagePath;
	private String writerNm;
	private LocalDateTime writedDttm;
}
