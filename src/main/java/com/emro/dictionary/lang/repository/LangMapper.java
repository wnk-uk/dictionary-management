package com.emro.dictionary.lang.repository;

import com.emro.dictionary.lang.LangRequest;
import com.emro.dictionary.lang.dto.LangDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LangMapper {

    @Select("SELECT req_id AS reqId, req_usr_nm AS reqUsrNm, req_dttm AS reqDttm FROM REQ")
    List<LangDTO> findByReqListAll(LangRequest request);

    @Select("SELECT * FROM REQ_DTL")
    LangDTO findByReqDetailListAll();


}
