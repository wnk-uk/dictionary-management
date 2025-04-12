package com.emro.dictionary.history.api;

import com.emro.dictionary.history.dto.MultlLangHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryResponseDTO;
import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.storage.service.EditorContentService;
import com.emro.dictionary.storage.service.FileStorageService;
import com.emro.dictionary.security.SecurityUtil;
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
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

	private final SecurityUtil securityUtil;
	private final HistoryService historyService;
	private final FileStorageService fileStorageService;
	private final EditorContentService editorContentService;

	@GetMapping("/{dtlId}")
	public ResponseEntity<List<RequestDetailHistoryResponseDTO>> getHistory(@PathVariable Long dtlId) {
		return ResponseEntity.ok(historyService.getHistoryByDtlId(dtlId));
	}

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> addComment(
			@RequestParam Long dtlId,
			@RequestParam String commentText,
			@RequestParam(required = false) String imageMapJson,
			@RequestParam(required = false) List<MultipartFile> files) throws IOException {
		Long writerId = securityUtil.getUserId();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> imageMap = imageMapJson != null ? objectMapper.readValue(imageMapJson, new TypeReference<Map<String, String>>() {}) : new HashMap<>();

		String imagePath = null;
		Map<String, String> filePathMap = new HashMap<>();
		if (files != null && !files.isEmpty()) {
			imagePath = fileStorageService.storeFiles(files, filePathMap);
		}

		String updatedCommentText = "";
		// 에디터 콘텐츠 처리 (임시 이미지 → 정식 이미지 URL 변환)
		if (commentText != null && !commentText.trim().isEmpty()) {
			updatedCommentText = editorContentService.moveTempImagesToUpload(commentText);
		}

		historyService.addHistory(dtlId, updatedCommentText, imagePath, writerId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/multlang/{multlangKey}")
	public ResponseEntity<List<MultlLangHistoryDTO>> getRequestHistory(@PathVariable("multlangKey") String multlangKey) {
		List<MultlLangHistoryDTO> resultDto;
		if ("all".equalsIgnoreCase(multlangKey)) resultDto = historyService.findAll();
		else resultDto = historyService.getRequestHistoryByMultlangKey(multlangKey);
		return ResponseEntity.ok(resultDto);
	}
}
