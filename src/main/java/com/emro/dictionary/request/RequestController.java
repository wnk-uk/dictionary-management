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

	@GetMapping("/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(
			@RequestParam(required = false) String acptSts) {

		List<MultLangListDTO> requests = requestService.getRequestsByAcptSts(acptSts);
		return ResponseEntity.ok(requests);
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> getPendingRequests(@RequestParam String regSts) {
		List<String> validStatuses = Arrays.asList("PENDING", "PROGRESS", "COMPLETE", "HOLDING");

		if (!validStatuses.contains(regSts.toUpperCase())) {
			return ResponseEntity.badRequest().body(0);
		}

		int count = requestService.getPendingRequests(regSts.toUpperCase());
		return ResponseEntity.ok(count);
	}

	@GetMapping("/top10")
	public ResponseEntity<List<MultLangListDTO>> getTop10RecentRequests(@RequestParam(required = false) String regSts) {
		List<MultLangListDTO> top10Requests = requestService.getTop10RecentRequests(regSts);
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
