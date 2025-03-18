package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	private final FileStorageService fileStorageService;

	public void saveRequest(MultLangRequestDTO request) throws IOException {
		// 1. Save to DIC_REQ
		requestMapper.insertRequest(request);

		// 2. Save details to DIC_REQ_DTL with files
		for (MultLangRequestDetailDTO detail : request.getDetails()) {
			String imagePath = null;
			if (detail.getFiles() != null && !detail.getFiles().isEmpty()) {
				// Save files and get the path (example logic)
				imagePath = fileStorageService.storeFiles(detail.getFiles());
			}
			detail.setImagePath(imagePath); // Set the path to the detail
			requestMapper.insertRequestDetail(request.getDicReqId(), detail);
		}
	}

	public List<MultLangListDTO> getAllRequestsExceptHOLDING() {
		return requestMapper.findAllRequestsExceptHOLDING();
	}

	public List<MultLangListDTO> getRequestsByAcptSts(String acptSts) {
			return requestMapper.findRequestsByAcptSts(acptSts);
	}

	public DashboardCountDTO findByAcptStatusCount() {
		return DashboardCountDTO.builder()
				.pendingCnt(requestMapper.findByAcptStatusCount("REQUEST"))
				.holdingCnt(requestMapper.findByAcptStatusCount("HOLDING"))
				.build();
	}

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
