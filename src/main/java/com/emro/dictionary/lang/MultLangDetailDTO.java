package com.emro.dictionary.lang;

import lombok.Data;

@Data
public class MultLangDetailDTO {
    private String multlangCcd;
    private String multlangKey;
    private String multlangTranslCont;
    private String multlangTranslContAbbr;
    private String multlangTyp;
    private String screenPath;
    private String sourcePath;
    private String comment;
}
