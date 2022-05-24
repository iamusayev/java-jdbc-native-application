package service;

import dao.repo.AuthorRepo;
import exception.DaoException;
import model.filter.AuthorFilter;
import util.Builder;

import java.sql.SQLException;
import java.util.Scanner;

import static model.constants.ExceptionConstants.DAO_EXCEPTION_CODE;
import static model.constants.ExceptionConstants.DAO_EXCEPTION_MESSAGE;

public class AuthorService {

    private AuthorService() {}
    private static final AuthorService INSTANCE = new AuthorService();


    private final Builder builder = Builder.getInstance();
    private  final AuthorRepo authorRepo = AuthorRepo.getInstance();
    Scanner sc = new Scanner(System.in);


    public void createAuthor() {
        authorRepo.createAuthor(builder.buildAuthor());
    }

    public void createAuthorAndBook() {
        var author = builder.buildAuthor();
        var book = builder.buildBook();
        try {
            authorRepo.createAuthorAndBook(author, book);
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }


    public void getAuthorById() {
        System.out.println("Input author id");
        var id = sc.nextLong();
        System.out.println(authorRepo.findById(id));
    }


    public void getAllAuthors() {
        System.out.println("Input limit (optional field)");
        var limit = sc.nextInt();
        System.out.println("Input offset (optional field) ");
        var offset = sc.nextInt();
        System.out.println("Write the name by which you would like to sort (optional field)");
        var firstName = sc.next();
        System.out.println("Write the lastname by which you would like to sort (optional field)");
        var lastName = sc.next();
        System.out.println(authorRepo.findAll(new AuthorFilter(limit, offset, firstName, lastName)));
    }

    public void getAuthorAndAllHisBooks() {
        System.out.println("Input author id ");
        var id = sc.nextLong();
        System.out.println(authorRepo.findAuthorAndAllHisBooks(id));
    }



            public static AuthorService getInstance() {
                return INSTANCE;
            }

} 
