package com.emro.dictionary.history.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class MultlLangHistoryDTO {
	private Long id;
	private Long dicReqId;
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
	private String reqUsrNm;
	private LocalDateTime reqDttm;
}
