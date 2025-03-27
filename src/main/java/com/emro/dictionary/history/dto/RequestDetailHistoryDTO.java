package com.emro.dictionary.history.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
@Data
public class RequestDetailHistoryDTO {
	private Long historyId;
	private Long dtlId;
	private String commentText;
	private String imagePath;
	private String writerNm;
	private Timestamp writedDttm;
}
