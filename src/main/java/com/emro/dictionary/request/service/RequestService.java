package com.emro.dictionary.request.service;

import com.emro.dictionary.request.dto.DashboardCountDTO;
import com.emro.dictionary.request.dto.MultLangListDTO;
import com.emro.dictionary.request.dto.MultLangRequestDTO;
import com.emro.dictionary.request.dto.UpdateRequestStatusDTO;

import java.io.IOException;
import java.util.List;

/**
 * 요청 관련 서비스를 정의하는 인터페이스
 */
public interface RequestService {
	/**
	 * 새로운 요청을 저장
	 * @param request 저장할 요청 데이터
	 * @throws IOException 파일 처리 중 발생할 수 있는 예외
	 */
	void saveRequest(MultLangRequestDTO request) throws IOException;

	/**
	 * HOLDING 상태가 아닌 모든 요청을 조회
	 * @param requester 요청자 (선택적, ROLE_USER일 경우 사용)
	 * @return 요청 리스트
	 */
	List<MultLangListDTO> getAllRequestsExceptHOLDING(String requester);

	/**
	 * 특정 상태(acptSts)에 따른 요청을 조회
	 * @param acptSts 조회할 상태
	 * @param requester 요청자 (선택적, ROLE_USER일 경우 사용)
	 * @return 요청 리스트
	 */
	List<MultLangListDTO> getRequestsByAcptSts(String acptSts, String requester);

	/**
	 * 상태에 따른 요청 갯수를 조회
	 * @return 상태별 갯수 DTO
	 */
	DashboardCountDTO findByAcptStatusCount();

	/**
	 * 특정 상태의 최신 요청 10개를 조회
	 * @param acptSts 조회할 상태
	 * @return 요청 리스트 (최대 10개)
	 */
	List<MultLangListDTO> getTop10RecentRequests(String acptSts, String requester);

	/**
	 * 선택된 요청들의 상태를 업데이트
	 * @param updateList 업데이트할 요청 상태 리스트
	 */
	void updateRequestStatus(List<UpdateRequestStatusDTO> updateList);

	/**
	 * decReqId를 이용한 detail 정보 호출
	 * @param dicReqId
	 * @return decReqId에 해당 하는 request Detail
	 */
	MultLangListDTO getRequestById(String dicReqId);
}