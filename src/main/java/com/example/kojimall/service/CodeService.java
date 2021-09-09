package com.example.kojimall.service;

import com.example.kojimall.domain.Code;
import com.example.kojimall.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.kojimall.domain.CodeVal.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CodeService {

    private final CodeRepository codeRepository;

    public List<Code> getCategoryList() {
        List<Code> codes = codeRepository.getCodes(CATEGORY_TYPE);
        if (codes.isEmpty()) {
            log.info("no such code");
        }
        return codes;
    }


    public Code getMainImageCode() {
        Code code = codeRepository.getCode(PRODUCT_MAIN);
        if (code == null) {
            log.info("no such code");
        }
        return code;
    }

    public Code getHoverImageCode() {
        Code code = codeRepository.getCode(PRODUCT_HOVER);
        if (code == null) {
            log.info("no such code");
        }
        return code;
    }

    public Code getNormalImageCode() {
        Code code = codeRepository.getCode(PRODUCT_NORMAL);
        if (code == null) {
            log.info("no such code");
        }
        return code;
    }

    public Code getCategoryCode(String cd) {
        return codeRepository.getCode(cd);
    }


}
