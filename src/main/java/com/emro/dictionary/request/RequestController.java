package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	@PostMapping("/multlang")
	public ResponseEntity<String> submitRequest(@RequestBody MultLangRequestDTO request) {
		requestService.saveRequest(request);
		return ResponseEntity.ok("Request submitted successfully");
	}
}
