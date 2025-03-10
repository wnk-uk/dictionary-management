package com.emro.dictionary.request;

import lombok.Data;

@Data
public class MultLangRequestDetailDTO {
	private String existingWord;
    private String multlangCcd;
    private String multlangKey;
    private String multlangTranslCont;
    private String multlangTranslContAbbr;
    private String multlangTyp;
    private String screenPath;
    private String sourcePath;
    private String comment;
}
