package com.example.kojimall.service;

import com.example.kojimall.domain.entity.Code;
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

    public void initCode() {
        codeRepository.save(new Code(MEMBER_TYPE, ROOT_TYPE, MEMBER_TYPE_NM, ROOT_TYPE_NM));
        codeRepository.save(new Code(MEMBER_NORMAL, MEMBER_TYPE, MEMBER_NORMAL_NM, MEMBER_TYPE_NM));
        codeRepository.save(new Code(MEMBER_VIP, MEMBER_TYPE, MEMBER_VIP_NM, MEMBER_TYPE_NM));
        codeRepository.save(new Code(MEMBER_ADMIN, MEMBER_TYPE, MEMBER_ADMIN_NM, MEMBER_TYPE_NM));
        codeRepository.save(new Code(CATEGORY_TYPE, ROOT_TYPE, CATEGORY_TYPE_NM, ROOT_TYPE_NM));
        codeRepository.save(new Code(CATEGORY_FOOD, CATEGORY_TYPE, CATEGORY_FOOD_NM, CATEGORY_TYPE_NM));
        codeRepository.save(new Code(CATEGORY_WEAR, CATEGORY_TYPE, CATEGORY_WEAR_NM, CATEGORY_TYPE_NM));
        codeRepository.save(new Code(FOOD_PROTEIN, CATEGORY_FOOD, FOOD_PROTEIN_NM, CATEGORY_FOOD_NM));
        codeRepository.save(new Code(FOOD_AA, CATEGORY_FOOD, FOOD_AA_NM, CATEGORY_FOOD_NM));
        codeRepository.save(new Code(FOOD_BOOSTER, CATEGORY_FOOD, FOOD_BOOSTER_NM, CATEGORY_FOOD_NM));
        codeRepository.save(new Code(IMAGE_TYPE, ROOT_TYPE, IMAGE_TYPE_NM, ROOT_TYPE_NM));
        codeRepository.save(new Code(IMAGE_PRODUCT, IMAGE_TYPE, IMAGE_PRODUCT_NM, IMAGE_TYPE_NM));
        codeRepository.save(new Code(PRODUCT_MAIN, IMAGE_PRODUCT, PRODUCT_MAIN_NM, IMAGE_PRODUCT_NM));
        codeRepository.save(new Code(PRODUCT_HOVER, IMAGE_PRODUCT, PRODUCT_HOVER_NM, IMAGE_PRODUCT_NM));
        codeRepository.save(new Code(PRODUCT_NORMAL, IMAGE_PRODUCT, PRODUCT_NORMAL_NM, IMAGE_PRODUCT_NM));
        codeRepository.save(new Code(LOGIN_TYPE, ROOT_TYPE, LOGIN_TYPE_NM, ROOT_TYPE_NM));
        codeRepository.save(new Code(LOGIN_EMAIL, LOGIN_TYPE, LOGIN_EMAIL_NM, LOGIN_TYPE_NM));
        codeRepository.save(new Code(LOGIN_KAKAO, LOGIN_TYPE, LOGIN_KAKAO_NM, LOGIN_TYPE_NM));
        codeRepository.save(new Code(LOGIN_NAVER, LOGIN_TYPE, LOGIN_NAVER_NM, LOGIN_TYPE_NM));
        codeRepository.save(new Code(LOGIN_GOOGLE, LOGIN_TYPE, LOGIN_GOOGLE_NM, LOGIN_TYPE_NM));


    }

    public List<Code> getCategoryList() {
        List<Code> codes = codeRepository.getCodes(CATEGORY_TYPE);
        if (codes.isEmpty()) {
            log.info("no such code");
        }
        return codes;
    }

    public Code getEmailLoginCode() {
        Code code = codeRepository.getCode(LOGIN_EMAIL);
        if (code == null) {
            log.info("no such code");
        }
        return code;
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


    public Code getMemberCode() {
        return codeRepository.getCode(MEMBER_NORMAL);
    }

    public Code getAdminCode() {
        return codeRepository.getCode(MEMBER_ADMIN);
    }

    public Code getKakaoLoginCode() {
        return codeRepository.getCode(LOGIN_KAKAO);
    }
}
