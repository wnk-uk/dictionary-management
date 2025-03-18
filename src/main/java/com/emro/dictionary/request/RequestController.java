package com.emro.dictionary.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	private final FileStorageService fileStorageService;

	@PostMapping(value = "/multlang", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> submitRequest(
			@RequestParam("reqUsrNm") String reqUsrNm,
			@RequestParam("details") String detailsJson,
			@RequestParam(value = "details[].files", required = false) List<MultipartFile> files) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<MultLangRequestDetailDTO> details = objectMapper.readValue(detailsJson, new TypeReference<List<MultLangRequestDetailDTO>>() {});

		MultLangRequestDTO request = new MultLangRequestDTO();
		request.setReqUsrNm(reqUsrNm);
		request.setDetails(details);

		// Distribute files to their respective details
// 파일을 각 detail에 분배하고 경로 설정
		int fileIndex = 0;
		for (int i = 0; i < details.size() && fileIndex < files.size(); i++) {
			List<MultipartFile> detailFiles = new ArrayList<>();
			while (fileIndex < files.size() && files.get(fileIndex) != null) {
				detailFiles.add(files.get(fileIndex));
				fileIndex++;
			}
			// 파일을 저장하고 경로를 String으로 받아서 설정
			if (!detailFiles.isEmpty()) {
				String imagePath = fileStorageService.storeFiles(detailFiles); // String 반환
				details.get(i).setImagePath(imagePath); // String을 설정
			}
		}

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
