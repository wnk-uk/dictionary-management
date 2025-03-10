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

	public DashboardCountDTO findByAcptStatusCount() {
		return DashboardCountDTO.builder()
				.pendingCnt(requestMapper.findByAcptStatusCount("REQ"))
				.holdingCnt(requestMapper.findByAcptStatusCount("HOLD"))
				.build();
	}

	public List<MultLangListDTO> getTop10RecentRequests(String reqSts) {
		return requestMapper.findTop10RecentHoldingRequests(reqSts);
	}

	/**
	 * 선택된 요청들의 상태를 업데이트하는 메서드
	 */
	public void updateRequestStatus(List<UpdateRequestStatusDTO> updateList) {
		for (UpdateRequestStatusDTO update : updateList) {
			Long dicReqId = update.getDicReqId();
			String newAcptSts = update.getAcptSts();

			// ✅ DIC_REQ 테이블의 acpt_sts 업데이트
			requestMapper.updateRequestAcptSts(dicReqId, newAcptSts);

			// ✅ DIC_REQ_DTL 테이블의 reg_sts 업데이트
			for (UpdateRequestDetailStatusDTO detail : update.getDetails()) {
				requestMapper.updateRequestDetailRegSts(detail.getId(), detail.getRegSts());
			}
		}
	}

}
