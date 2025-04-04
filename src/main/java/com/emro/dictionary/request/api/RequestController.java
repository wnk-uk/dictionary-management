package com.emro.dictionary.request.api;

import com.emro.dictionary.request.storage.service.FileStorageService;
import com.emro.dictionary.request.storage.service.EditorContentService;
import com.emro.dictionary.request.service.resolver.RequestServiceResolver;
import com.emro.dictionary.request.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestServiceResolver serviceResolver;
	private final FileStorageService fileStorageService;
	private final EditorContentService editorContentService;

	@PostMapping(value = "/multlang", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> submitRequest(
			@RequestParam("reqUsrNm") String reqUsrNm,
			@RequestParam("details") String detailsJson,
			@RequestParam(value = "editorContent", required = false) String editorContent,
			@RequestParam(value = "files", required = false) List<MultipartFile> files
	) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		List<MultLangRequestDetailDTO> details = objectMapper.readValue(detailsJson, new TypeReference<>() {});

		MultLangRequestDTO request = new MultLangRequestDTO();
		request.setReqUsrNm(reqUsrNm);
		request.setDetails(details);

		// 파일 업로드
		String imagePath = null;
		Map<String, String> filePathMap = new HashMap<>();
		if (files != null && !files.isEmpty()) {
			imagePath = fileStorageService.storeFiles(files, filePathMap);
		}

		// 에디터 콘텐츠 처리 (임시 이미지 → 정식 이미지 URL 변환)
		if (editorContent != null && !editorContent.trim().isEmpty()) {
			String updated = editorContentService.moveTempImagesToUpload(editorContent);
			request.setEditorContent(updated);
		} else {
			request.setEditorContent(editorContent);
		}

		request.setFiles(files);
		request.setImagePath(imagePath);
		String username = serviceResolver.getUsername();
		serviceResolver.getService().saveRequest(request, username);
		return ResponseEntity.ok("Request submitted successfully");
	}

	/**
	 * Request List 상태에 따른 조회 API (ROLE 참조)
	 */
	@GetMapping("/{acptSts}/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(@PathVariable String acptSts) {
		String username = serviceResolver.getUsername();
		if ("all".equalsIgnoreCase(acptSts)) {
			return ResponseEntity.ok(serviceResolver.getService().getAllRequestsExceptHOLDING(username));
		}
		return ResponseEntity.ok(serviceResolver.getService().getRequestsByAcptSts(acptSts.toUpperCase(), username));
	}

	/**
	 * 상태에 따른 request 요청 갯수 조회 API
	 */
	@GetMapping("/count")
	public ResponseEntity<DashboardCountDTO> findCountAll() {
		return ResponseEntity.ok(serviceResolver.getService().findByAcptStatusCount());
	}

	/**
	 * 상태에 따른 최상단 10개 조회 API
	 */
	@GetMapping("/{acptSts}/top10")
	public ResponseEntity<List<MultLangListDTO>> getTop10RecentRequests(@PathVariable String acptSts) {
		String username = serviceResolver.getUsername();

		return ResponseEntity.ok(serviceResolver.getService().getTop10RecentRequests(acptSts.toUpperCase(), username));
	}

	/**
	 * 선택된 요청들의 상태 업데이트 API
	 */
	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateRequestStatus(@RequestBody List<UpdateRequestStatusDTO> requestList) {
		String username = serviceResolver.getUsername();
		serviceResolver.getService().updateRequestStatus(requestList, username);
		return ResponseEntity.ok("✅ Status Updated successfully");
	}

	/**
	 * Request Id를 이용한 detail 조회
	 */
	@GetMapping("/detail/{dicReqId}")
	public ResponseEntity<MultLangListDTO> getRequestDetail(@PathVariable Long dicReqId) {
		MultLangListDTO request = serviceResolver.getService().getRequestById(dicReqId);
		return request != null ? ResponseEntity.ok(request) : ResponseEntity.notFound().build();
	}
}