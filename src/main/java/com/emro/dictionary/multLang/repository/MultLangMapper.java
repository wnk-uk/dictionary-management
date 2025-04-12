package com.emro.dictionary.multLang.repository;

import com.emro.dictionary.multLang.dto.MultLangDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MultLangMapper {

	@Select("""
			SELECT 
				m.*
				,(SELECT COUNT(*) 
					FROM REQ_DTL d
					INNER JOIN REQ r ON d.req_id = r.req_id
					WHERE d.multlang_key = m.multlang_key) AS historyCnt
			FROM Multlang m
	""")
	List<MultLangDTO> findAll();

	@Select("SELECT DISTINCT multlang_key FROM MULTLANG WHERE LOWER(multlang_key) LIKE LOWER(CONCAT('%', #{request}, '%'))")
	List<String> findExistingWords(String request);

	@Select("""
	SELECT *
	FROM Multlang
	WHERE multlang_key=#{multlangKey}
	""")
	MultLangDTO findByMultlangKey(@Param("multlangCcd") String multlangCcd, @Param("multlangKey") String multlangKey);

	@Update("""
        UPDATE MULTLANG
        SET multlang_transl_cont = #{multlangTranslCont},
            multlang_transl_cont_abbr = #{multlangTranslContAbbr},
            multlang_typ = #{multlangTyp},
            rmk = #{rmk},
            modr_id = #{modrId},
            mod_dttm = #{modDttm},
            multlang_mod_dttm = #{multlangModDttm}
        WHERE multlang_ccd = #{multlangCcd}
        AND multlang_key = #{multlangKey}
    """)
	void updateMultlang(MultLangDTO multLangDTO);

	@Insert("""
        INSERT INTO MULTLANG (
            multlang_ccd, multlang_key, multlang_transl_cont, multlang_transl_cont_abbr, 
            multlang_typ, rmk, regr_id, reg_dttm, multlang_mod_dttm, sts, multlang_abbr_use_yn
        ) VALUES (
            #{multlangCcd}, #{multlangKey}, #{multlangTranslCont}, #{multlangTranslContAbbr},
            #{multlangTyp}, #{rmk}, #{regrId}, #{regDttm}, #{multlangModDttm}, #{sts}, #{multlangAbbrUseYn}
        )
    """)
	void insertMultlang(MultLangDTO multLangDTO);
}

