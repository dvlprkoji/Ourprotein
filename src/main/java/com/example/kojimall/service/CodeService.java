package com.example.kojimall.service;

import com.example.kojimall.domain.Code;
import com.example.kojimall.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

    private final CodeRepository codeRepository;

    private static final String CD_CATEGORY = "CAT_TY";

    public List<Code> getCategoryList() {
        List<Code> codes = codeRepository.getCodes(CD_CATEGORY);
        if (codes.isEmpty()) {
            log.info("no such code");
        }
        return codes;
    }

}
