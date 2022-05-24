package model.filter;

public class AuthorFilter {

    private final Integer limit;
    private final Integer offset;
    private final String firstName;
    private final String lastname;


    public AuthorFilter(Integer limit, Integer offset, String firstName, String lastname) {
        this.limit = limit;
        this.offset = offset;
        this.firstName = firstName;
        this.lastname = lastname;
    }


    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }
}
