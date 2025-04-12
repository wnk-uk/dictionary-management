package com.emro.dictionary.history.repository;

import com.emro.dictionary.history.dto.MultlLangHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryResponseDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Detail History 관리
 */
@Mapper
public interface HistoryMapper {

	@Select("""
	    SELECT h.*, u.usr_nm AS writer_nm
	    FROM REQ_DTL_HIS h
	    LEFT JOIN users u ON h.writer_id = u.id
	    WHERE h.dtl_id = #{dtlId}
	    ORDER BY h.written_dttm ASC
	""")
	List<RequestDetailHistoryResponseDTO> findHistoryByDtlId(Long dtlId);

	@Insert("""
		INSERT INTO REQ_DTL_HIS (dtl_id, comment_text, image_path, writer_id, written_dttm)
		VALUES (#{dtlId}, #{commentText}, #{imagePath}, #{writerId}, CURRENT_TIMESTAMP)
		""")
	@Options(useGeneratedKeys = true, keyProperty = "dtlHisId")
	void insertHistory(RequestDetailHistoryDTO history);

	@Select("""
		SELECT d.*, u.usr_nm AS req_user_nm, r.req_dttm
		FROM REQ_DTL d
		INNER JOIN REQ r ON d.req_id = r.req_id
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE d.multlang_key = #{multlangKey}
		ORDER BY r.req_dttm DESC
        """)
	List<MultlLangHistoryDTO> findByMultlangKey(String multlangKey);

	@Select("""
		SELECT d.*, u.usr_nm AS req_user_nm, r.req_dttm
		FROM REQ_DTL d
		INNER JOIN REQ r ON d.req_id = r.req_id
		LEFT JOIN users u ON r.req_user_id = u.id
		ORDER BY r.req_dttm DESC
        """)
	List<MultlLangHistoryDTO> findAll();

	@Select("""
    SELECT
        h.dtl_his_id,
        d.req_id,
        d.existing_word,
        d.multlang_ccd,
        d.multlang_key,
        d.multlang_transl_cont,
        d.multlang_suggested_transl_cont,
        d.multlang_transl_cont_abbr,
        d.multlang_typ,
        d.screen_path,
        d.source_path,
        d.comment,
        d.reg_sts,
        u.usr_nm AS req_user_nm,
        r.req_dttm
    FROM req_dtl_his h
    JOIN req_dtl d ON h.dtl_id = d.dtl_id
    JOIN req r ON d.req_id = r.req_id
    LEFT JOIN users u ON r.req_user_id = u.id
    WHERE d.multlang_key = #{multlangKey}

    UNION

    SELECT
        NULL AS dtl_his_id,
        NULL AS req_id,
        CASE
            WHEN t.category IN ('label', 'button', '공통코드') AND t.english_modified IS NOT NULL THEN t.english
            ELSE NULL
        END AS existing_word,
        lang_info.multlang_ccd,
        COALESCE(t.english_modified, t.english) AS multlang_key,
        CASE
            WHEN lang_info.multlang_ccd = 'en_US' THEN COALESCE(t.english_modified, t.english)
            WHEN lang_info.multlang_ccd = 'ko_KR' THEN COALESCE(NULLIF(t.korean_modified, ''), t.korean)
        END AS multlang_transl_cont,
        NULL AS multlang_suggested_transl_cont,
        NULL AS multlang_transl_cont_abbr,
        CASE
            WHEN t.category IN ('label', 'button') THEN t.category
            WHEN t.category = '공통코드' THEN 'CommonCode'
            ELSE 'statement'
        END AS multlang_typ,
        t.screen_path,
        t.source_path,
        t.symbol_protein AS comment,
        'PENDING' AS reg_sts,
        REGEXP_REPLACE(t.requester, '^\\d{2}\\.\\d{2}\\.\\d{2}_[^_]*_(.*?)(\\s|$).*', '\\1') AS req_user_nm,
        TO_DATE('20' || SPLIT_PART(t.requester, '_', 1), 'YYYY.MM.DD') AS req_dttm
    FROM temp_table t
    JOIN (
        SELECT 'ko_KR' AS multlang_ccd
        UNION ALL
        SELECT 'en_US' AS multlang_ccd
    ) lang_info
    WHERE (
        (t.category IN ('label', 'button', '공통코드') AND (t.english IS NOT NULL OR t.english_modified IS NOT NULL))
        OR
        (t.category NOT IN ('label', 'button', '공통코드') AND
         (t.korean IS NOT NULL OR t.korean_modified IS NOT NULL OR t.english IS NOT NULL OR t.english_modified IS NOT NULL))
    )
    AND COALESCE(t.english_modified, t.english) = #{multlangKey}
    ORDER BY req_dttm DESC NULLS LAST
""")
	List<MultlLangHistoryDTO> findMultLangHistoryByKey(@Param("multlangKey") String multlangKey);

}
