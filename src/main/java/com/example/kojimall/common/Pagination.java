package com.example.kojimall.common;


import lombok.Data;

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

    public void pageInfo(int page, int range, int listCnt) {

        this.page = page;
        this.range = range;
        this.listCnt = listCnt;

        //전체 페이지수
        this.pageCnt = (int)Math.ceil((double)listCnt/(double)listSize);

        //시작 페이지
        this.startPage = (range - 1) * rangeSize + 1 ;

        //끝 페이지
        this.endPage = range * rangeSize;

        //게시판 시작번호
        this.startList = (page - 1) * listSize;

        // 게시판 끝번호
        this.endList = this.startList + this.listSize;

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