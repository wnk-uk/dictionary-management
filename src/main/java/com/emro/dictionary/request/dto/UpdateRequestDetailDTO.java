package com.emro.dictionary.request.dto;

import lombok.Data;

@Data
public class UpdateRequestDetailDTO {
	private Long dtlId;
	private String existingWord;
	private String multlangCcd;
	private String multlangKey;
	private String multlangTranslCont;
	private String multlangSuggestedTranslCont;
	private String multlangTranslContAbbr;
	private String multlangTyp;
	private String commentText;

}
