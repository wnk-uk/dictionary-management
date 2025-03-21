package com.emro.dictionary.multLang.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultLangDTO {
    private String multlangCcd;
    private String multlangKey;
    private String multlangTranslCont;
    private LocalDateTime multlangModDttm;
    private String multlangTranslContAbbr;
    private String multlangAbbrUseYn;
    private String multlangTyp;
    private String rmk;
    private String sts;
    private String regrId;
    private LocalDateTime regDttm;
    private String modrId;
    private LocalDateTime modDttm;
    private String multlangTranslFnlCont;

}
