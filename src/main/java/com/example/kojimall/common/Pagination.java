package com.example.kojimall.common;


import com.example.kojimall.domain.entity.Item;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Data
public class Pagination {
    private int listSize = 8;                //초기값으로 목록개수를 8로 셋팅

    private int rangeSize = 10;            //초기값으로 페이지범위를 10으로 셋팅

    private int page;

    private int range;

    private int listCnt;

    private int pageCnt;

    private int startPage;

    private int startList;

    private int endList;

    private int endPage;

    private boolean prev;

    private boolean next;

    public void pageInfo(Page page) {

        this.page = page.getNumber()+1;
        this.range = (page.getTotalPages() / rangeSize) + 1;
        this.listCnt = ((Long)page.getTotalElements()).intValue();

        //전체 페이지수
        this.pageCnt = page.getTotalPages();

        //시작 페이지
        this.startPage = (range - 1) * rangeSize + 1 ;

        //끝 페이지
        this.endPage = range * rangeSize;

        //게시판 시작번호
        this.startList = (this.page - 1) * listSize;

        // 게시판 끝번호
        this.endList = this.startList + this.listSize-1;

        //이전 버튼 상태
        this.prev = range == 1 ? false : true;

        //다음 버튼 상태
        this.next = endPage > pageCnt ? false : true;
        if (this.endPage > this.pageCnt) {
            this.endPage = this.pageCnt;
            this.next = false;
        }

    }


}