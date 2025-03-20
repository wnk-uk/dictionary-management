package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

	private final RequestMapper requestMapper;

	/**
	 * 요청 저장
	 */
	public void saveRequest(MultLangRequestDTO request) throws IOException {
		// DIC_REQ에 저장 (editorContent와 imagePath 포함)
		requestMapper.insertRequest(request);

		// DIC_REQ_DTL에 상세 정보 저장
		for (MultLangRequestDetailDTO detail : request.getDetails()) {
			requestMapper.insertRequestDetail(request.getDicReqId(), detail);
		}
	}

	/**
	 *  STS가 HOLDING이 아닌 데이터 조회 (ROLE X)
	 */
	public List<MultLangListDTO> getAllRequestsExceptHOLDING() {
		return requestMapper.findAllRequestsExceptHOLDING();
	}

	/**
	 * 특정 ACPT_STS 값으로 조회 (ROLE X)
	 */
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts) {
			return requestMapper.findRequestsByAcptSts(acptSts);
	}

	/**
	 * HOLDING이 아닌, 특정 requester의 데이터 조회
	 */
	public List<MultLangListDTO> getRequestsByRequesterExceptHOLDING(String requester) {
		return requestMapper.findRequestsByRequesterExceptHOLDING(requester);
	}

	/**
	 * 특정 acpt_sts와 requester로 데이터 조회
	 */
	public List<MultLangListDTO> getRequestsByAcptStsAndRequester(String acptSts, String requester) {
		return requestMapper.findRequestsByAcptStsAndRequester(acptSts, requester);
	}

	/**
	 * 요청 상태에 따른 갯수 세기
	 */
	public DashboardCountDTO findByAcptStatusCount() {
		return DashboardCountDTO.builder()
				.pendingCnt(requestMapper.findByAcptStatusCount("REQUEST"))
				.holdingCnt(requestMapper.findByAcptStatusCount("HOLDING"))
				.build();
	}

	/**
	 * 요청시간 상위 Top10 특정 요청 가져오기 (특정 regSts)
	 */
	public List<MultLangListDTO> getTop10RecentRequests(String reqSts) {
		return requestMapper.findTop10RecentHOLDINGingRequests(reqSts);
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
