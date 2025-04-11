package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.multLang.service.MultLangService;
import com.emro.dictionary.notification.service.NotificationService;
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
			HistoryService historyService,
			NotificationService notificationService
	) {
		super(requestMapper, multLangService, historyService, notificationService);
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(Long userId) {
		return requestMapper.findAllRequestsExceptHOLDING(null); // userId 무시
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, Long userId) {
		return requestMapper.findRequestsByAcptSts(acptSts, null); // userId 무시
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, Long userId) {
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, null);
	}
}