package kr.co.leem.libs.jpa.params;

public class PageInfo {
    protected PageParams pageParam;
    protected int totalPage;
    protected long totalElements;
    protected boolean hasNextPage;
    protected boolean hasPreviousPage;
    protected boolean isFirstPage;
    protected boolean isLastPage;

    public PageParams getPageParam() {
        return pageParam;
    }

    public void setPageParam(PageParams value) {
        this.pageParam = value;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int value) {
        this.totalPage = value;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long value) {
        this.totalElements = value;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean value) {
        this.hasNextPage = value;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean value) {
        this.hasPreviousPage = value;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean value) {
        this.isFirstPage = value;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean value) {
        this.isLastPage = value;
    }

}
