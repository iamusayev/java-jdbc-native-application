package service;

import dao.repo.BookRepo;
import model.filter.BookFilter;
import util.Builder;

import java.util.Scanner;

public class BookService {

    private BookService() {
    }

    private static final BookService INSTANCE = new BookService();


    private final BookRepo bookRepo = BookRepo.getInstance();
    private final Builder builder = Builder.getInstance();
    Scanner sc = new Scanner(System.in);


    public void addBookToAuthor() {
        System.out.println("Input author id ");
        var authorId = sc.nextLong();
        var book = builder.buildBook();
        bookRepo.addBookToAuthor(authorId, book);
    }


    public void getBookById() {
        System.out.println(bookRepo.findById(builder.buildId("book id")));
    }


    public void getAllBooks() {
        System.out.println("Input limit (optional field)");
        var limit = sc.nextInt();
        System.out.println("Input offset (optional field) ");
        var offset = sc.nextInt();
        System.out.println("Input book name (optional field)");
        var name = sc.next();
        System.out.println("Input genre");
        var genre = sc.next();
        System.out.println(bookRepo.findAll(new BookFilter(limit, offset, name, genre)));
    }


    public void deleteBook() {
        bookRepo.deleteById(builder.buildId("book id"));
    }


    public void deleteAllAuthorsBooks() {
        bookRepo.deleteBooksByAuthorId(builder.buildId("author id"));
    }


            public static BookService getInstance() {
                return INSTANCE;
            }

}
