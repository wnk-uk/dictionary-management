package com.emro.dictionary.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
			@RequestParam(value = "editorContent", required = false) String editorContent,
			@RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<MultLangRequestDetailDTO> details = objectMapper.readValue(detailsJson, new TypeReference<List<MultLangRequestDetailDTO>>() {});

		MultLangRequestDTO request = new MultLangRequestDTO();
		request.setReqUsrNm(reqUsrNm);
		request.setDetails(details);

		// editorContent 처리
		String textContent = null;
		if (editorContent != null && !editorContent.trim().isEmpty()) {
			textContent = Jsoup.parse(editorContent).text();
		}

		// 모든 파일을 한 번에 저장 (DIC_REQ의 image_path에 저장)
		String imagePath = null;
		if (files != null && !files.isEmpty()) {
			imagePath = fileStorageService.storeFiles(files);
		}

		// MultLangRequestDTO에 editorContent와 imagePath 설정
		request.setEditorContent(textContent);
		request.setFiles(files);
		request.setImagePath(imagePath);

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
