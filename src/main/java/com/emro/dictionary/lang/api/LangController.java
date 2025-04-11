package com.emro.dictionary.lang.api;

import com.emro.dictionary.lang.dto.LangDTO;
import com.emro.dictionary.lang.LangRequest;
import com.emro.dictionary.lang.service.LangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lang")
@RequiredArgsConstructor
public class LangController {

    private final LangService langService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LangDTO langDTO) {
        return ResponseEntity.ok("데이터 저장 완료");
    }

    @GetMapping("/req/lists")
    public ResponseEntity<List<LangDTO>> findByReqList(@ModelAttribute LangRequest request) {
        return ResponseEntity.ok(langService.findByReqListAll(request));
    }

    @GetMapping("/lists")
    public ResponseEntity<LangDTO> findByDetailList(@RequestParam LangRequest langRequest) {
        return ResponseEntity.ok(langService.findByReqDetailListAll(langRequest));
    }

}
