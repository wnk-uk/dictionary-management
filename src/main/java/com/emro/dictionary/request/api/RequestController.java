package com.emro.dictionary.request.api;

import com.emro.dictionary.request.storage.FileStorageService;
import com.emro.dictionary.request.service.resolver.RequestServiceResolver;
import com.emro.dictionary.request.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 파일 저장 및 경로 생성
		String imagePath = null;
		Map<String, String> filePathMap = new HashMap<>();
		if (files != null && !files.isEmpty()) {
			imagePath = fileStorageService.storeFiles(files, filePathMap);
		}

		// 에디터 콘텐츠 처리
		if (editorContent != null && !editorContent.trim().isEmpty()) {
			String updatedContent = replaceBase64WithFilePath(editorContent, filePathMap);
			request.setEditorContent(updatedContent); // 단일 String으로 설정
		}

		request.setFiles(files);
		request.setImagePath(imagePath);
		String username = serviceResolver.getUsername();
		serviceResolver.getService().saveRequest(request, username);
		return ResponseEntity.ok("Request submitted successfully");
	}

	// Base64 이미지를 파일 경로로 대체하는 메서드
	private String replaceBase64WithFilePath(String editorContent, Map<String, String> filePathMap) {
		Document doc = Jsoup.parse(editorContent);
		Elements images = doc.select("img[src^=data:image/]");

		for (Element img : images) {
			String base64Src = img.attr("src");
			String fileName = filePathMap.keySet().stream()
					.filter(key -> filePathMap.get(key).contains(base64Src))
					.findFirst()
					.orElse(null);
			if (fileName != null) {
				String relativePath = "/uploads/" + fileName;
				img.attr("src", relativePath); // 경로로 대체
			}
		}
		return doc.body().html();
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