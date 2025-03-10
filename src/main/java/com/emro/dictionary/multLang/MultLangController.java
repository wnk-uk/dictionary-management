package com.emro.dictionary.multLang;

import com.emro.dictionary.request.MultLangRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multlang")
@RequiredArgsConstructor
public class MultLangController {

	private final MultLangService multLangService;

	@GetMapping(value = "/list")
	public ResponseEntity<List<MultLangDTO>> getMultlangList() {
		List<MultLangDTO> multLangDTOs = multLangService.getAllMultlangs();
		return ResponseEntity.ok(multLangDTOs);
	}

	@GetMapping("/search")
	public ResponseEntity<List<String>> searchExistingWord(@RequestParam String request) {
		List<String> words = multLangService.searchExistingWord(request);
		return ResponseEntity.ok(words);
	}


}
