package com.emro.dictionary.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/list")
	public ResponseEntity<List<MultLangListDTO>> getRequests(
			@RequestParam(required = false) String acptSts) {

		List<MultLangListDTO> requests = requestService.getRequestsByAcptSts(acptSts);
		return ResponseEntity.ok(requests);
	}
}
