package my.entity;

public class GetListFilter {
    public int page;
    public int limit;
    public String search;
    public String sortBy;
    public String sortType;

    public GetListFilter() {

    }

    public GetListFilter(int page, int limit, String search, String sortBy, String sortType) {
        this.page = page;
        this.limit = limit;
        this.search = search;
        this.sortBy = sortBy;
        this.sortType = sortType;
    }
}
