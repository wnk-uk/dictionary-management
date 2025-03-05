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
}
