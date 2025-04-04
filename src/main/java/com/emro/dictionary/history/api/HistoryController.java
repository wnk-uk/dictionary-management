package com.emro.dictionary.history.api;

import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.request.storage.EditorContentService;
import com.emro.dictionary.request.storage.FileStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private final HistoryService historyService;
	private final FileStorageService fileStorageService;
	private final EditorContentService editorContentService;

	@GetMapping("/{dtlId}")
	public List<RequestDetailHistoryDTO> getHistory(@PathVariable Long dtlId) {
		return historyService.getHistoryByDtlId(dtlId);
	}

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> addComment(
			@RequestParam Long dtlId,
			@RequestParam String commentText,
			@RequestParam(required = false) String imageMapJson,
			@RequestParam(required = false) List<MultipartFile> files) throws IOException {
		String writerNm = SecurityContextHolder.getContext().getAuthentication().getName();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> imageMap = imageMapJson != null ? objectMapper.readValue(imageMapJson, new TypeReference<Map<String, String>>() {}) : new HashMap<>();

		String imagePath = null;
		Map<String, String> filePathMap = new HashMap<>();
		if (files != null && !files.isEmpty()) {
			imagePath = fileStorageService.storeFiles(files, filePathMap);
		}

		String updatedCommentText = commentText;
//		if (commentText != null && !commentText.trim().isEmpty()) {
//			updatedCommentText = editorContentService.replaceBase64WithFilePath(commentText, imageMap);
//		}

		historyService.addHistory(dtlId, updatedCommentText, imagePath, writerNm);
		return ResponseEntity.ok().build();
	}
}
