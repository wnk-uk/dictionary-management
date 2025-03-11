package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	@PostMapping("/multlang")
	public ResponseEntity<String> submitRequest(@RequestBody MultLangRequestDTO request) {
		requestService.saveRequest(request);
		return ResponseEntity.ok("Request submitted successfully");
	}

	@GetMapping("/{acptSts}/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(
			@PathVariable String acptSts) {

		if ("ALL".equalsIgnoreCase(acptSts)) {
			return ResponseEntity.ok(requestService.getAllRequestsExceptHOLDING());
		}

		return ResponseEntity.ok(requestService.getRequestsByAcptSts(acptSts));
	}

	@GetMapping("/count")
	public ResponseEntity<DashboardCountDTO> findCountAll() {
		return ResponseEntity.ok(requestService.findByAcptStatusCount());
	}

	@GetMapping("/{acptSts}/top10")
	public ResponseEntity<List<MultLangListDTO>> getTop10RecentRequests(@PathVariable String acptSts) {
		List<MultLangListDTO> top10Requests = requestService.getTop10RecentRequests(acptSts.toUpperCase());
		return ResponseEntity.ok(top10Requests);
	}

	/**
	 * 선택된 요청들의 상태를 업데이트하는 API
	 */
	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateRequestStatus(@RequestBody List<
			UpdateRequestStatusDTO> requestList) {
		requestService.updateRequestStatus(requestList);  // 여러 개 처리하는 서비스 메서드
		return ResponseEntity.ok("✅ Status Updated successfully");
	}


}
