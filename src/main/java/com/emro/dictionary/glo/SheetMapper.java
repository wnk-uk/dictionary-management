package com.emro.dictionary.glo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SheetMapper {

    // 데이터 저장 (CLOB 필드에 JSON 문자열 저장)
    @Insert("INSERT INTO SHEET_DATA(json_data) VALUES (#{jsonData})")
    void insertSheetData(@Param("jsonData") String jsonData);

    // 최신 데이터 불러오기
    @Select("SELECT json_data FROM SHEET_DATA ORDER BY id DESC LIMIT 1")
    String getSheetData();

}
