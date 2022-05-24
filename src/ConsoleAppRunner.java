import service.AuthorService;
import service.BookService;
import util.Info;

import java.util.Scanner;

public class ConsoleAppRunner {
    public static void main(String[] args) {

        Info info = Info.getInstance();
        Scanner sc = new Scanner(System.in);

        BookService bookService = BookService.getInstance();
        AuthorService authorService = AuthorService.getInstance();


        while (true) {
            info.mainInfo();
            var select = sc.next();
            if (select.equals("1")) {
                info.authorsInfo();
                switch (sc.next()) {
                    case "1":
                        authorService.createAuthor();
                        break;
                    case "2":
                        authorService.createAuthorAndBook();
                        break;
                    case "3":
                        authorService.getAuthorById();
                        break;
                    case "4":
                        authorService.getAllAuthors();
                        break;
                    case "5":
                        authorService.getAuthorAndAllHisBooks();
                }
            } else if (select.equals("2")) {
                info.booksInfo();
                switch (sc.next()) {
                    case "1":
                        bookService.addBookToAuthor();
                        break;
                    case "2":
                        bookService.getAllBooks();
                        break;
                    case "3":
                        bookService.getBookById();
                        break;
                    case "4":
                        bookService.deleteBook();
                        break;
                    case "5":
                        bookService.deleteAllAuthorsBooks();
                }
            }
        }
    }
}
