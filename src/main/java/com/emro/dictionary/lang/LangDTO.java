package com.emro.dictionary.lang;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LangDTO {
    private int dicReqId;
    private String reqUsrNm;
    private LocalDateTime reqDttm;
}
