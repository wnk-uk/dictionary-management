package com.emro.dictionary.request.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardCountDTO {
	private int pendingCnt;
	private int holdingCnt;

}
