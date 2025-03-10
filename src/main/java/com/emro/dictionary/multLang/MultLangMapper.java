package com.emro.dictionary.multLang;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MultLangMapper {

	@Select("SELECT * FROM Multlang")
	List<MultLangDTO> findAll();

	@Select("SELECT DISTINCT multlang_key FROM MULTLANG WHERE LOWER(multlang_key) LIKE LOWER(CONCAT('%', #{request}, '%'))")
	List<String> findExistingWords(String request);


}

