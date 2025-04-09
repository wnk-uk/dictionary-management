package com.emro.dictionary.request.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRequestStatusDTO {
	private Long reqId;
	private String acptSts;
	private List<UpdateRequestDetailStatusDTO> details;
}

