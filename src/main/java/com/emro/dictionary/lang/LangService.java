package com.emro.dictionary.lang;

import com.emro.dictionary.glo.SheetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LangService {

    private final LangMapper langMapper;

    public List<LangDTO> findByReqListAll(LangRequest langRequest) {
        return langMapper.findByReqListAll(langRequest);
    }

    public LangDTO findByReqDetailListAll(LangRequest langRequest) {
        return langMapper.findByReqDetailListAll();
    }

    public List<MultLangDTO> getAllMultlangs() {
        return langMapper.findAll();
    }

    @Transactional
    public void saveRequest(MultLangRequestDTO request) {
        // 1. DIC_REQ 테이블에 저장
        langMapper.insertRequest(request);

        // 2. DIC_REQ_DTL 테이블에 각 단어 정보 저장
        for (MultLangDetailDTO detail : request.getDetails()) {
            langMapper.insertRequestDetail(request.getDicReqId(), detail);
        }
    }
}
