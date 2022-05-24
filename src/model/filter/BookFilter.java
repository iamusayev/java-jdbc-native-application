package model.filter;

public class BookFilter {

    private final Integer limit;
    private final Integer offset;
    private final String name;
    private final String genre;


    public BookFilter(Integer limit, Integer offset, String name, String genre) {
        this.limit = limit;
        this.offset = offset;
        this.name = name;
        this.genre = genre;
    }


    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }
}
