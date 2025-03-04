package com.emro.dictionary.glo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/glo")
@RequiredArgsConstructor
public class SheetController {

    private final SheetService luckysheetService;

    @PostMapping("/save")
    public String saveLuckysheetData(@RequestBody SheetDTO luckysheetDTO) {
        luckysheetService.saveLuckysheetData(luckysheetDTO);
        return "데이터 저장 완료";
    }

    @GetMapping("/load")
    public SheetDTO loadLuckysheetData() {
        return luckysheetService.loadLuckysheetData();
    }

}
