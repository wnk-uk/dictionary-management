package com.emro.dictionary.request.dto;

import lombok.Data;

@Data
public class MultLangRequestDetailDTO {
	private Long dtlId;
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
	private String rmk;

}
