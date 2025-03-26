package com.emro.dictionary.request.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MultLangRequestDetailDTO {
	private Long id;
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
