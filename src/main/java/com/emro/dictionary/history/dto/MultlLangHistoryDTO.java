package com.emro.dictionary.history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MultlLangHistoryDTO {
	private Long dtlHisId;
	private Long reqId;
	private String existingWord;
	private String multlangCcd;
	private String multlangKey;
	private String multlangTranslCont;
	private String multlangSuggestedTranslCont;
	private String multlangTranslContAbbr;
	private String multlangTyp;
	private String screenPath;
	private String sourcePath;
	private String comment;
	private String regSts;
	private String reqUserNm;
	private LocalDateTime reqDttm;
}
