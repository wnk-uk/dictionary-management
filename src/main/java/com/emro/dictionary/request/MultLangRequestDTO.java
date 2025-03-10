package com.emro.dictionary.request;

import java.util.List;
import lombok.Data;

@Data
public class MultLangRequestDTO {
    private Long dicReqId;
    private String reqUsrNm;
    private List<MultLangRequestDetailDTO> details;
}