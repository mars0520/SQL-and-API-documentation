package com.mars.demo.bean.dto;

/**
 * @author xzp
 * @description 分页
 * @date 2021/1/20
 **/
public class PageInfoDTO {

    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 每页条数
     */
    private Integer pageSize;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageInfoDTO() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

    public PageInfoDTO(Integer currentPage, Integer pageSize) {
        this.currentPage = (null == currentPage || currentPage < 0) ? 1 : currentPage;
        this.pageSize = (null == pageSize || pageSize < 0 || pageSize > 500) ? 10 : pageSize;
    }

    @Override
    public String toString() {
        return "PageInfoDTO{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
