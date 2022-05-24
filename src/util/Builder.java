package util;

import dao.entity.Author;
import dao.entity.Book;
import model.enums.Genre;

import java.time.LocalDate;
import java.util.Scanner;

public class Builder {

    private Builder() {
    }

    private static final Builder INSTANCE = new Builder();

    Scanner sc = new Scanner(System.in);

    public Author buildAuthor() {
        System.out.println("Input name ");
        var firstName = sc.next();
        System.out.println("Input lastname");
        var lastName = sc.next();
        System.out.println("Select year of birth ");
        var year = sc.nextInt();
        System.out.println("Select month of year");
        var month = sc.nextInt();
        System.out.println("Select day of month");
        var day = sc.nextInt();
        return Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDay(LocalDate.of(year, month, day))
                .build();
    }


    public Book buildBook() {
        System.out.println("Input name please");
        String name = sc.next();
        System.out.println("Select genre");
        var genre = buildGenre();
        System.out.println("Input page count");
        var pageCount = sc.nextInt();
        System.out.println("Input publishing year");
        var publishingYear = sc.nextInt();
        return Book.builder()
                .name(name)
                .genre(genre)
                .pageCount(pageCount)
                .publishingYear(publishingYear)
                .build();
    }


    public Long buildId(String idName) {
        System.out.println("Input %s ".formatted(idName));
        return sc.nextLong();
    }


    public Genre buildGenre() {
        String genre = null;
        System.out.println("""
                         
                    1.TRAVEL
                    2.STORY
                    3.LEGEND
                    4.HISTORY
                    5.SELF_DEVELOPMENT
                    6.COMPUTER_SCIENCE
                    7.PROGRAMMING
                    8.NOVELS
                                
                """);

        switch (sc.next()) {
            case "1" -> genre = "TRAVEL";
            case "2" -> genre = "STORY";
            case "3" -> genre = "LEGEND";
            case "4" -> genre = "HISTORY";
            case "5" -> genre = "SELF_DEVELOPMENT";
            case "6" -> genre = "COMPUTER_SCIENCE";
            case "7" -> genre = "PROGRAMMING";
            case "8" -> genre = "NOVELS";
            default -> genre = "AMATEUR_LITERATURE";
        }
        return Genre.valueOf(genre);
    }


            public static Builder getInstance() {
                return INSTANCE;
            }

}
