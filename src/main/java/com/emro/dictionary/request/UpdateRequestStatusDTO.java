package com.emro.dictionary.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRequestStatusDTO {
	private Long dicReqId;
	private String acptSts;
	private List<UpdateRequestDetailStatusDTO> details;
}

