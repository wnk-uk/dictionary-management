package com.emro.dictionary.lang;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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


}
