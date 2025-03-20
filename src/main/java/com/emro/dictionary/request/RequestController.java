package com.emro.dictionary.request;

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

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;
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

		requestService.saveRequest(request);
		return ResponseEntity.ok("Request submitted successfully");
	}

	/**
	* Request List 상태에 따른 조회 API ( ROLE 참조 )
	*/
	@GetMapping("/{acptSts}/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(
			@PathVariable String acptSts) {

		// 현재 인증된 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName(); // 현재 사용자 이름
		boolean isUserRole = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")); // ROLE USER 인지 확인

		// 요청 상태가 전체 일 경우
		if ("ALL".equalsIgnoreCase(acptSts)) {
			if (isUserRole) {
				// ROLE_USER라면 작성자 본인의 데이터만 반환
				return ResponseEntity.ok(requestService.getRequestsByRequesterExceptHOLDING(username));
			} else {
				// ROLE_USER가 아니라면 전체 데이터 반환
				return ResponseEntity.ok(requestService.getAllRequestsExceptHOLDING());
			}
		} 
		// 전체가 아닐 경우
		else {
			if (isUserRole) {
				// ROLE_USER라면 작성자 본인의 데이터만 반환
				return ResponseEntity.ok(requestService.getRequestsByAcptStsAndRequester(acptSts, username));
			} else {
				// ROLE_USER가 아니라면 상태에 따른 전체 데이터 반환
				return ResponseEntity.ok(requestService.getRequestsByAcptSts(acptSts));
			}
		}
	}

	/**
	 * 상태에 따른 request 요청 갯수 조회 API
	 */
	@GetMapping("/count")
	public ResponseEntity<DashboardCountDTO> findCountAll() {
		return ResponseEntity.ok(requestService.findByAcptStatusCount());
	}

	/**
	 * 상태에 따른 최상단 10개 조회 API
	 */
	@GetMapping("/{acptSts}/top10")
	public ResponseEntity<List<MultLangListDTO>> getTop10RecentRequests(@PathVariable String acptSts) {
		List<MultLangListDTO> top10Requests = requestService.getTop10RecentRequests(acptSts.toUpperCase());
		return ResponseEntity.ok(top10Requests);
	}

	/**
	 * 선택된 요청들의 상태 업데이트 API
	 */
	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateRequestStatus(@RequestBody List<
			UpdateRequestStatusDTO> requestList) {
		requestService.updateRequestStatus(requestList);  // 여러 개 처리하는 서비스 메서드
		return ResponseEntity.ok("✅ Status Updated successfully");
	}


}
