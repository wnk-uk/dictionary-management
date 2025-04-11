package com.emro.dictionary.history.repository;

import com.emro.dictionary.history.dto.MultlLangHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryResponseDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
		SELECT d.*, u.usr_nm AS req_usr_nm, r.req_dttm
		FROM REQ_DTL d
		INNER JOIN REQ r ON d.req_id = r.req_id
		LEFT JOIN users u ON r.req_user_id = u.id
		WHERE d.multlang_key = #{multlangKey}
		ORDER BY r.req_dttm ASC
        """)
	List<MultlLangHistoryDTO> findByMultlangKey(String multlangKey);
}
