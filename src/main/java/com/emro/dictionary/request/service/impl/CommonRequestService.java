package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.request.repository.RequestMapper;
import com.emro.dictionary.request.dto.*;
import com.emro.dictionary.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * 요청 서비스의 공통 로직을 제공하는 클래스
 */
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommonRequestService implements RequestService {
	protected final RequestMapper requestMapper;

	@Override
	public void saveRequest(MultLangRequestDTO request) throws IOException {
		requestMapper.insertRequest(request);
		for (MultLangRequestDetailDTO detail : request.getDetails()) {
			requestMapper.insertRequestDetail(request.getDicReqId(), detail);
		}
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(String requester) {
		return requestMapper.findAllRequestsExceptHOLDING(requester);
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, String requester) {
		return requestMapper.findRequestsByAcptSts(acptSts, requester);
	}

	@Override
	public DashboardCountDTO findByAcptStatusCount() {
		return DashboardCountDTO.builder()
				.pendingCnt(requestMapper.findByAcptStatusCount("REQUEST"))
				.holdingCnt(requestMapper.findByAcptStatusCount("HOLDING"))
				.build();
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, String requester) {
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, requester);
	}

	@Override
	public void updateRequestStatus(List<UpdateRequestStatusDTO> updateList) {
		for (UpdateRequestStatusDTO update : updateList) {
			requestMapper.updateRequestAcptSts(update.getDicReqId(), update.getAcptSts());
			for (UpdateRequestDetailStatusDTO detail : update.getDetails()) {
				requestMapper.updateRequestDetailRegSts(detail.getId(), detail.getRegSts());
			}
		}
	}

	@Override
	public MultLangListDTO getRequestById(String dicReqId) {
		return requestMapper.findByDicReqId(dicReqId);
	}
}