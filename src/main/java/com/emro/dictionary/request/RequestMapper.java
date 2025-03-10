package com.emro.dictionary.request;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestMapper {

	// 요청 저장 (DIC_REQ 테이블에 데이터 삽입)
	@Insert("""
        INSERT INTO DIC_REQ (req_usr_nm, req_dttm, sts, acpt_sts)
        VALUES (#{reqUsrNm}, NOW(), 'C', 'REQ')
    """)
	@Options(useGeneratedKeys = true, keyProperty = "dicReqId")
	void insertRequest(MultLangRequestDTO request);

	// 요청 상세 저장 (DIC_REQ_DTL 테이블에 데이터 삽입)
	@Insert("""
        INSERT INTO DIC_REQ_DTL (dic_req_id, existing_word, multlang_ccd, multlang_key, multlang_transl_cont, 
                                 multlang_transl_cont_abbr, multlang_typ, screen_path, 
                                 source_path, comment, reg_sts)
        VALUES (#{dicReqId}, #{detail.existingWord}, #{detail.multlangCcd}, #{detail.multlangKey}, #{detail.multlangTranslCont}, 
                #{detail.multlangTranslContAbbr}, #{detail.multlangTyp}, #{detail.screenPath}, 
                #{detail.sourcePath}, #{detail.comment}, 'PENDING')
    """)
	void insertRequestDetail(@Param("dicReqId") Long dicReqId, @Param("detail") MultLangRequestDetailDTO detail);

	// 특정 ACPT_STS 값으로 조회
	@Select("""
        SELECT * FROM DIC_REQ 
        WHERE acpt_sts = #{acptSts}
        ORDER BY req_dttm DESC
    """)
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "com.emro.dictionary.request.RequestMapper.findRequestDetails"))
	})
	List<MultLangListDTO> findRequestsByAcptSts(@Param("acptSts") String acptSts);

	// STS가 HOLDING이 아닌 데이터 조회
	@Select("""
        SELECT * FROM DIC_REQ 
        WHERE acpt_sts <> 'HOLD'
        ORDER BY req_dttm DESC
    """)
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "com.emro.dictionary.request.RequestMapper.findRequestDetails"))
	})
	List<MultLangListDTO> findAllRequestsExceptHold();

	// RequestDetail 조회 (DIC_REQ_DTL 테이블)
	@Select("""
        SELECT * FROM DIC_REQ_DTL 
        WHERE dic_req_id = #{dicReqId}
    """)
	List<MultLangDetailListDTO> findRequestDetails(@Param("dicReqId") Long dicReqId);

	// 요청 상태에 따른 갯수 세기
	@Select("""
		SELECT COALESCE(COUNT(*), 0) FROM DIC_REQ_DTL
		WHERE reg_sts = #{regSts}
	""")
	Integer countPendingRequestDetails(String regSts);

	// 요청시간 상위 Top10 요청 가져오기 (전체)
	@Select("""
    SELECT req.dic_req_id, req.req_usr_nm, req.req_dttm, req.acpt_sts
    FROM DIC_REQ req
    WHERE req.dic_req_id IN (
        SELECT DISTINCT dic_req_id 
        FROM DIC_REQ_DTL 
        WHERE reg_sts <> 'HOLDING'
    )
    ORDER BY req.req_dttm DESC
    LIMIT 10
	""")
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetailsByDicReqId"))
	})
	List<MultLangListDTO> findTop10RecentRequests();

	// 요청시간 상위 Top10 특정 요청 가져오기 (특정 regSts)
	@Select("""
    SELECT req.dic_req_id, req.req_usr_nm, req.req_dttm, req.acpt_sts
    FROM DIC_REQ req
    WHERE req.dic_req_id IN (
        SELECT DISTINCT dic_req_id 
        FROM DIC_REQ_DTL 
        WHERE reg_sts = #{regSts}
    )
    ORDER BY req.req_dttm DESC
    LIMIT 10
	""")
	@Results({
			@Result(property = "dicReqId", column = "dic_req_id", id = true),
			@Result(property = "reqUsrNm", column = "req_usr_nm"),
			@Result(property = "reqDttm", column = "req_dttm"),
			@Result(property = "acptSts", column = "acpt_sts"),
			@Result(property = "details", column = "dic_req_id",
					many = @Many(select = "findRequestDetailsByDicReqId"))
	})
	List<MultLangListDTO> findTop10RecentHoldingRequests(@Param("regSts") String regSts);

	// 특정 dicReqId에 대한 요청 상세 정보 가져오기
	@Select("""
    SELECT * FROM DIC_REQ_DTL
    WHERE dic_req_id = #{dicReqId}
	""")
	List<MultLangDetailListDTO> findRequestDetailsByDicReqId(@Param("dicReqId") Long dicReqId);

	/**
	 * 선택된 요청들의 상태를 업데이트하는 SQL 쿼리
	 */
	@Update("""
        UPDATE DIC_REQ
        SET acpt_sts = #{acptSts}
        WHERE dic_req_id = #{dicReqId}
    """)
	void updateRequestAcptSts(@Param("dicReqId") Long dicReqId, @Param("acptSts") String acptSts);

	@Update("""
        UPDATE DIC_REQ_DTL
        SET reg_sts = #{regSts}
        WHERE id = #{id}
    """)
	void updateRequestDetailRegSts(@Param("id") Long id, @Param("regSts") String regSts);

}
