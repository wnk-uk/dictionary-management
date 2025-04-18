package com.emro.dictionary.request.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MultLangListDTO {
	private Long reqId;
	private String reqUserNm;
	private LocalDateTime reqDttm;
	private List<MultLangDetailDTO> details;
	private String imagePath;
	private String editorContent;
	private String acptSts;
}

