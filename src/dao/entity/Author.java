package dao.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private List<Book> books;

    public Author() {
    }

    public Author(final Long id,final String firstName,final String lastName,final LocalDate birthDay, final List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.books = books;
    }

    public static AuthorBuilder builder(){
        return new AuthorBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Author{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthDay=" + birthDay +
               ", books=" + books +
               '}';
    }

    public static class AuthorBuilder {

        private Long id;
        private String firstName;
        private String lastName;
        private LocalDate birthDay;
        private List<Book> books;

        public AuthorBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public AuthorBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorBuilder birthDay(final LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public AuthorBuilder books(final List<Book> books){
            this.books  = books;
            return this;
        }


        public Author build() {
            return new Author(this.id, this.firstName, this.lastName, this.birthDay,this.books);

        }
    }
}
