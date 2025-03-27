package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.multLang.service.MultLangService;
import com.emro.dictionary.request.dto.MultLangListDTO;
import com.emro.dictionary.request.repository.RequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ROLE_SYSADMIN을 위한 요청 서비스 구현
 */
@Service("sysAdminRequestService")
@Slf4j
public class SysAdminRequestServiceImpl extends CommonRequestService {

	public SysAdminRequestServiceImpl(
			RequestMapper requestMapper,
			MultLangService multLangService,
			HistoryService historyService
	) {
		super(requestMapper, multLangService, historyService);
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