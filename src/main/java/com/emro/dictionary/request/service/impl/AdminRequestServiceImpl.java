package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.multLang.service.MultLangService;
import com.emro.dictionary.request.dto.UpdateRequestDetailStatusDTO;
import com.emro.dictionary.request.dto.UpdateRequestStatusDTO;
import com.emro.dictionary.request.repository.RequestMapper;
import com.emro.dictionary.request.dto.MultLangListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ROLE_ADMIN을 위한 요청 서비스 구현
 */
@Service("adminRequestService")
@Slf4j
public class AdminRequestServiceImpl extends CommonRequestService {

	public AdminRequestServiceImpl(RequestMapper requestMapper, MultLangService multLangService) {
		super(requestMapper, multLangService);
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(String requester) {
		return requestMapper.findAllRequestsExceptHOLDING(null); // requester 무시
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, String requester) {
		return requestMapper.findRequestsByAcptSts(acptSts, null); // requester 무시
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, String requester) {
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, null);
	}
}