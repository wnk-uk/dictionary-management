package com.emro.dictionary.history.repository;

import com.emro.dictionary.history.dto.MultlLangHistoryDTO;
import com.emro.dictionary.history.dto.RequestDetailHistoryDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Detail History 관리
 */
@Mapper
public interface HistoryMapper {

	@Select("""
		SELECT * 
		FROM DIC_REQ_DTL_HIS 
		WHERE dtl_id = #{dtlId} 
		ORDER BY writed_dttm ASC
		""")
	List<RequestDetailHistoryDTO> findHistoryByDtlId(Long dtlId);

	@Insert("""
		INSERT INTO DIC_REQ_DTL_HIS (dtl_id, comment_text, image_path, writer_nm, writed_dttm)
		VALUES (#{dtlId}, #{commentText}, #{imagePath}, #{writerNm}, CURRENT_TIMESTAMP)
		""")
	void insertHistory(RequestDetailHistoryDTO history);

	@Select("""
		SELECT d.*, r.req_usr_nm, r.req_dttm
        FROM DIC_REQ_DTL d
        INNER JOIN DIC_REQ r ON d.dic_req_id = r.dic_req_id
        WHERE d.multlang_key = #{multlangKey}
        ORDER BY r.req_dttm ASC
        """)
	List<MultlLangHistoryDTO> findByMultlangKey(String multlangKey);
}
