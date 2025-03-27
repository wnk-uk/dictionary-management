package com.emro.dictionary.request.api;

import com.emro.dictionary.request.storage.FileStorageService;
import com.emro.dictionary.request.service.resolver.RequestServiceResolver;
import com.emro.dictionary.request.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * request 관련 REST API 제공
 */
@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestServiceResolver serviceResolver;
	private final FileStorageService fileStorageService;


	/**
	 * 유저의 등록 요청 API
	 */
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

		serviceResolver.getService().saveRequest(request);
		return ResponseEntity.ok("Request submitted successfully");
	}

	/**
	 * Request List 상태에 따른 조회 API (ROLE 참조)
	 */
	@GetMapping("/{acptSts}/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(@PathVariable String acptSts) {
		String username = serviceResolver.getUsername();

		if ("ALL".equalsIgnoreCase(acptSts)) {
			return ResponseEntity.ok(serviceResolver.getService().getAllRequestsExceptHOLDING(username));
		}
		return ResponseEntity.ok(serviceResolver.getService().getRequestsByAcptSts(acptSts, username));
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