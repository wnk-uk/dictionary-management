package com.emro.dictionary.request.service.impl;

import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.multLang.service.MultLangService;
import com.emro.dictionary.request.dto.*;
import com.emro.dictionary.request.repository.RequestMapper;
import com.emro.dictionary.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * 요청 서비스의 공통 로직을 제공하는 클래스
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommonRequestService implements RequestService {
	protected final RequestMapper requestMapper;
	protected final MultLangService multLangService;
	protected final HistoryService historyService;

	@Override
	public void saveRequest(MultLangRequestDTO request, String requester) throws IOException {
		requestMapper.insertRequest(request);
		for (MultLangRequestDetailDTO detail : request.getDetails()) {
			requestMapper.insertRequestDetail(request.getDicReqId(), detail);
			if (detail.getComment() != null && !detail.getComment().isEmpty()) {
				Long dtlId = detail.getId();
				if (dtlId == null) {
					throw new IllegalStateException("dtlId was not generated for detail: " + detail);
				}
				historyService.addHistory(dtlId, request.getEditorContent(), request.getImagePath(), requester);
				historyService.addHistory(dtlId, detail.getComment(), null, requester);
			}
		}
	}

	@Override
	public List<MultLangListDTO> getAllRequestsExceptHOLDING(String requester) {
		return requestMapper.findAllRequestsExceptHOLDING(requester);
	}

	@Override
	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts, String requester) {
		return requestMapper.findRequestsByAcptSts(acptSts, requester);
	}

	@Override
	public DashboardCountDTO findByAcptStatusCount() {
		return DashboardCountDTO.builder()
				.pendingCnt(requestMapper.findByAcptStatusCount("REQUEST"))
				.holdingCnt(requestMapper.findByAcptStatusCount("HOLDING"))
				.build();
	}

	@Override
	public List<MultLangListDTO> getTop10RecentRequests(String acptSts, String requester) {
		return requestMapper.findTop10RecentHOLDINGingRequests(acptSts, requester);
	}

	@Override
	public void updateRequestStatus(List<UpdateRequestStatusDTO> updateList, String requester) {
		for (UpdateRequestStatusDTO update : updateList) {
			Long dicReqId = update.getDicReqId();

			// 1. DIC_REQ_DTL 상태 업데이트
			for (UpdateRequestDetailStatusDTO detail : update.getDetails()) {
				requestMapper.updateRequestDetailRegSts(detail.getId(), detail.getRegSts(), detail.getMultlangTranslCont());

				// regSts가 ACCEPTANCE일 경우 MULTLANG 처리
				if ("ACCEPTANCE".equals(detail.getRegSts())) {
					MultLangRequestDetailDTO detailRecord = requestMapper.findRequestDetailByDicReqId(detail.getId());

					multLangService.saveOrUpdateMultlang(
							detailRecord.getMultlangCcd(),
							detailRecord.getMultlangKey(),
							detail.getMultlangTranslCont(),
							detailRecord.getMultlangTranslContAbbr(),
							detailRecord.getMultlangTyp(),
							detail.getRmk(),
							requester
					);
				}
			}

			// 2. DIC_REQ_DTL 상태 조회
			List<String> detailStatuses = requestMapper.getDetailStatusesByDicReqId(dicReqId);

			// 3. DIC_REQ 상태 결정 및 업데이트
			String newAcptSts = determineAcptSts(detailStatuses);
			requestMapper.updateRequestAcptSts(dicReqId, newAcptSts);
		}
	}

	protected String determineAcptSts(List<String> detailStatuses) {
		if (detailStatuses.isEmpty()) {
			return "PENDING";
		}

		boolean hasPending = detailStatuses.contains("PENDING");
		boolean hasHolding = detailStatuses.contains("HOLDING");
		boolean allComplete = detailStatuses.stream()
				.allMatch(status -> status.equals("ACCEPTANCE") || status.equals("REJECT"));

		if (allComplete) {
			return "COMPLETE";
		} else if (hasHolding) {
			return "HOLDING";
		} else if (!hasPending) {
			return "PROGRESS";
		} else {
			return "PENDING";
		}
	}

	@Override
	public MultLangListDTO getRequestById(Long dicReqId) {
		return requestMapper.findByDicReqId(dicReqId);
	}
}