package com.emro.dictionary.request.dto;

import lombok.Data;

@Data
public class UpdateRequestDetailStatusDTO {
	private Long dtlId;
	private String regSts;
	private String multlangTranslCont;
	private String rmk;
	private String comment;
}
