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
 * ROLE_USER를 위한 요청 서비스 구현
 */
@Service("userRequestService")
@Slf4j
public class UserRequestServiceImpl extends CommonRequestService {

	public UserRequestServiceImpl(
			RequestMapper requestMapper,
			MultLangService multLangService,
			HistoryService historyService,
			NotificationService notificationService
	) {
		super(requestMapper, multLangService, historyService, notificationService);
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("ROLE_USER requires userId");
		}
		return requestMapper.findAllRequestsExceptHOLDING(userId);
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("ROLE_USER requires userId");
		}
		return requestMapper.findRequestsByAcptSts(acptSts, userId);
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("ROLE_USER requires userId");
		}
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, userId);
	}
}