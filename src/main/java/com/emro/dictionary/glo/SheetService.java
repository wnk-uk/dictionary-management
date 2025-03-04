package com.emro.dictionary.glo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SheetService {

    private final SheetMapper luckysheetMapper;
    private final ObjectMapper objectMapper;

    // 데이터 저장
    public void saveLuckysheetData(SheetDTO luckysheetDTO) {
        try {
            String jsonData = objectMapper.writeValueAsString(luckysheetDTO.getSheetData());
            luckysheetMapper.insertSheetData(jsonData);
        } catch (Exception e) {
            throw new RuntimeException("데이터 저장 실패", e);
        }
    }

    // 데이터 불러오기
    public SheetDTO loadLuckysheetData() {
        String jsonData = luckysheetMapper.getSheetData();
        SheetDTO dto = new SheetDTO();
        if (jsonData == null) return dto;
        try {
            dto.setSheetData(objectMapper.readValue(jsonData, List.class));
        } catch (Exception e) {
            throw new RuntimeException("데이터 변환 실패", e);
        }
        return dto;
    }

}
