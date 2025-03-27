package com.emro.dictionary.history.api;

import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.service.HistoryService;
import com.emro.dictionary.request.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

	private final HistoryService historyService;
	private final FileStorageService fileStorageService;

	@GetMapping("/{dtlId}")
	public List<RequestDetailHistoryDTO> getHistory(@PathVariable Long dtlId) {
		return historyService.getHistoryByDtlId(dtlId);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addComment(@RequestParam Long dtlId,
	                                       @RequestParam String commentText,
	                                       @RequestParam(required = false) List<MultipartFile> files) throws IOException {
		String writerNm = SecurityContextHolder.getContext().getAuthentication().getName();
		String imagePath = null;
		if (files != null && !files.isEmpty()) {
			// 이미지 저장 로직 (예: 파일 시스템 또는 S3)
			imagePath = fileStorageService.storeFiles(files);
		}
		historyService.addHistory(dtlId, commentText, imagePath, writerNm);
		return ResponseEntity.ok().build();
	}
}
