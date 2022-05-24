package dao.entity;

import model.enums.Genre;

import java.util.Objects;

public class Book {

    private Long id;
    private String name;
    private Genre genre;
    private Integer pageCount;
    private Integer publishingYear;
    private Author author;


    public Book() {
    }

    public Book(final Long id,final String name,final Genre genre,final Integer pageCount,final Integer publishingYear,final Author author) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.pageCount = pageCount;
        this.publishingYear = publishingYear;
        this.author = author;
    }


    public static Book.BookBuilder builder(){
        return new BookBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", genre=" + genre +
               ", pageCount=" + pageCount +
               ", publishingYear=" + publishingYear +
               ", author=" + author +
               '}';
    }

    public static class BookBuilder{

        private Long id;
        private String name;
        private Genre genre;
        private Integer pageCount;
        private Integer publishingYear;
        private Author author;


        public BookBuilder id(final Long id){
            this.id = id;
            return this;
        }

        public BookBuilder name(final String name){
            this.name = name;
            return this;
        }

        public BookBuilder genre(final Genre genre){
            this.genre = genre;
            return this;
        }

        public BookBuilder pageCount(final Integer pageCount){
            this.pageCount = pageCount;
            return this;
        }

        public BookBuilder publishingYear(final Integer publishingYear){
            this.publishingYear = publishingYear;
            return this;
        }

        public BookBuilder author(final Author author){
            this.author  = author;
            return this;
        }

        public Book build(){
            return new Book(this.id,this.name,this.genre,this.pageCount,this.publishingYear, this.author);

        }
    }
}
