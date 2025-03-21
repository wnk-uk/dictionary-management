package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.request.repository.RequestMapper;
import com.emro.dictionary.request.dto.MultLangListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ROLE_USER를 위한 요청 서비스 구현
 */
@Service("userRequestService")
@Slf4j
public class UserRequestServiceImpl extends CommonRequestService {

	public UserRequestServiceImpl(RequestMapper requestMapper) {
		super(requestMapper);
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(String requester) {
		if (requester == null) {
			throw new IllegalArgumentException("ROLE_USER requires requester");
		}
		return requestMapper.findAllRequestsExceptHOLDING(requester);
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, String requester) {
		if (requester == null) {
			throw new IllegalArgumentException("ROLE_USER requires requester");
		}
		return requestMapper.findRequestsByAcptSts(acptSts, requester);
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, String requester) {
		if (requester == null) {
			throw new IllegalArgumentException("ROLE_USER requires requester");
		}
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, requester);
	}
}