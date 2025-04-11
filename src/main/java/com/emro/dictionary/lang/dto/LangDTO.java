package com.emro.dictionary.lang.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LangDTO {
    private int dicReqId;
    private String reqUserNm;
    private LocalDateTime reqDttm;
}
