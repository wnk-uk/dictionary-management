package com.emro.dictionary.lang;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MULTLANG")
public class MultLang {
    @Id
    @Generated
    private Long id;

    private String multlangCcd;
    private String multlangKey;
    private String multlangTranslCont;
    private LocalDateTime multlangModDttm;
    private String multlangTranslContAbbr;
    private String multlangAbbrUseYn; // CHAR(1) → String
    private String multlangTyp;
    private String rmk;
    private String sts; // CHAR(1) → String
    private String regrId;
    private LocalDateTime regDttm;
    private String modrId;
    private LocalDateTime modDttm;
    private String multlangTranslFnlCont;
}
