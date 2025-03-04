package com.emro.dictionary.glo;

import lombok.Data;

import java.util.List;

@Data
public class SheetDTO {
    private Long id;
    private List<Object> sheetData; // JSON 데이터를 List 형태로 저장
}
