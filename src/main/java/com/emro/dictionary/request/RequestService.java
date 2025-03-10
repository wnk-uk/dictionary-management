package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

	private final RequestMapper requestMapper;

	public void saveRequest(MultLangRequestDTO request) {
		// 1. DIC_REQ 테이블에 저장
		requestMapper.insertRequest(request);

		// 2. DIC_REQ_DTL 테이블에 각 단어 정보 저장
		for (MultLangRequestDetailDTO detail : request.getDetails()) {
			requestMapper.insertRequestDetail(request.getDicReqId(), detail);
		}
	}
}
