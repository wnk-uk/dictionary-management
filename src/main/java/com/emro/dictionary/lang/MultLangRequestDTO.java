package com.emro.dictionary.lang;

import java.util.List;
import lombok.Data;

@Data
public class MultLangRequestDTO {
    private Long dicReqId;
    private String reqUsrNm;
    private List<MultLangDetailDTO> details;
}