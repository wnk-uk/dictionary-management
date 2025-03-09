package com.emro.dictionary.lang;

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
    public String save(@RequestBody LangDTO langDTO) {
        return "데이터 저장 완료";
    }

    @GetMapping("/req/lists")
    public List<LangDTO> findByReqList(@ModelAttribute LangRequest request) {
        return langService.findByReqListAll(request);
    }

    @GetMapping("/lists")
    public LangDTO findByDetailList(@RequestParam LangRequest langRequest) {
        return langService.findByReqDetailListAll(langRequest);
    }

    @GetMapping(value = "/multlang/list")
    public ResponseEntity<List<MultLangDTO>> getMultlangList() {
        List<MultLangDTO> multLangDTOs = langService.getAllMultlangs();
        return ResponseEntity.ok(multLangDTOs);
    }

    @PostMapping("/multlang/request")
    public ResponseEntity<String> submitRequest(@RequestBody MultLangRequestDTO request) {
        langService.saveRequest(request);
        return ResponseEntity.ok("Request submitted successfully");
    }

}
