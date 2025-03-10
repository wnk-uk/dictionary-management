package com.emro.dictionary.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MultLangListDTO {
	private Long dicReqId;
	private String reqUsrNm;
	private LocalDateTime reqDttm;
	private List<MultLangDetailListDTO> details;
	private String acptSts;
}
