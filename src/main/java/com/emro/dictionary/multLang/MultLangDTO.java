package com.emro.dictionary.multLang;

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

    public MultLangDTO(MultLang multlang) {
        this.multlangCcd = multlang.getMultlangCcd();
        this.multlangKey = multlang.getMultlangKey();
        this.multlangTranslCont = multlang.getMultlangTranslCont();
        this.multlangModDttm = multlang.getMultlangModDttm();
        this.multlangTranslContAbbr = multlang.getMultlangTranslContAbbr();
        this.multlangAbbrUseYn = multlang.getMultlangAbbrUseYn();
        this.multlangTyp = multlang.getMultlangTyp();
        this.rmk = multlang.getRmk();
        this.sts = multlang.getSts();
        this.regrId = multlang.getRegrId();
        this.regDttm = multlang.getRegDttm();
        this.modrId = multlang.getModrId();
        this.modDttm = multlang.getModDttm();
        this.multlangTranslFnlCont = multlang.getMultlangTranslFnlCont();
    }
}
