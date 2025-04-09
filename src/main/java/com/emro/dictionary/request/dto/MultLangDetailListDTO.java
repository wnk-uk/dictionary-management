package com.emro.dictionary.request.dto;

import lombok.Data;

@Data
public class MultLangDetailListDTO {
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
}
