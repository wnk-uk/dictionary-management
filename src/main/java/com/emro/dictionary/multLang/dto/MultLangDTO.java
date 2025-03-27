package com.emro.dictionary.multLang.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
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
