package com.emro.dictionary.glo.api;

import com.emro.dictionary.glo.dto.SheetDTO;
import com.emro.dictionary.glo.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/glo")
@RequiredArgsConstructor
public class SheetController {

    private final SheetService sheetService;

    @PostMapping("/save")
    public String saveLuckysheetData(@RequestBody SheetDTO sheetDTO) {
        sheetService.saveSheetData(sheetDTO);
        return "데이터 저장 완료";
    }

    @GetMapping("/load")
    public SheetDTO loadLuckysheetData() {
        return sheetService.loadSheetData();
    }

}
