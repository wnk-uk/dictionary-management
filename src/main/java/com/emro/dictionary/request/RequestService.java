package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts) {
		if (acptSts != null && !acptSts.isEmpty()) {
			// 특정 ACPT_STS 값으로 조회
			return requestMapper.findRequestsByAcptSts(acptSts);
		} else {
			// STS가 HOLDING이 아닌 데이터 조회
			return requestMapper.findAllRequestsExceptHold();
		}
	}
}
