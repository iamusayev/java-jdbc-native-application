package dao.repo;

import dao.entity.Author;
import dao.entity.Book;
import exception.DaoException;
import model.enums.Genre;
import model.filter.AuthorFilter;
import pool.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static model.constants.ExceptionConstants.*;

public class AuthorRepo {

    private AuthorRepo() {}
    private static final AuthorRepo INSTANCE = new AuthorRepo();


    private static final String FIND_AUTHOR_AND_BOOKS_SQL = """
                        
            SELECT
            authors.id,
            first_name,
            last_name,
            birth_day,
            b.id,
            name,
            genre,
            page_count,
            publishing_year
            FROM authors
            INNER JOIN books b on authors.id = b.author_id
                        
            """;

    private static final String FIND_ALL_SQL = """
                        
            SELECT
            id,
            first_name,
            last_name,
            birth_day
            FROM authors
                        
            """;

    private static final String SAVE_AUTHOR_SQL = """
                        
            INSERT INTO authors (first_name, last_name, birth_day) 
            VALUES (?, ?, ?)
                        
                        
            """;


    private static final String SAVE_BOOK_SQL = """
                        
            INSERT INTO books ( author_id,name, genre, page_count, publishing_year)
            VALUES (?, ?, ?, ?, ?)
                        
            """;


    private static final String FIND_BY_ID = FIND_ALL_SQL + " WHERE id =? ";

    public void createAuthor(Author author) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_AUTHOR_SQL);) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setObject(3, author.getBirthDay());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }


    public void createAuthorAndBook(Author author, Book book) throws SQLException {
        Connection connection = null;
        PreparedStatement authorPrepareStatement = null;
        PreparedStatement bookPrepareStatement = null;

        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);

            authorPrepareStatement = connection.prepareStatement(SAVE_AUTHOR_SQL, RETURN_GENERATED_KEYS);
            bookPrepareStatement = connection.prepareStatement(SAVE_BOOK_SQL);

            authorPrepareStatement.setString(1, author.getFirstName());
            authorPrepareStatement.setString(2, author.getLastName());
            authorPrepareStatement.setObject(3, author.getBirthDay());
            authorPrepareStatement.executeUpdate();
            var generatedKeys = authorPrepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookPrepareStatement.setLong(1, generatedKeys.getLong("id"));
            }
            bookPrepareStatement.setString(2, book.getName());
            bookPrepareStatement.setString(3, book.getGenre().toString());
            bookPrepareStatement.setInt(4, book.getPageCount());
            bookPrepareStatement.setInt(5, book.getPublishingYear());
            bookPrepareStatement.executeUpdate();

            connection.commit();

        } catch (Exception ex) {
            if (connection != null) {
                connection.rollback();
                throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (bookPrepareStatement != null) {
                bookPrepareStatement.close();
            }
            if (authorPrepareStatement != null) {
                authorPrepareStatement.close();
            }
        }
    }

    public Optional<Author> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Author author = new Author();
            if (resultSet.next()) {
                author = buildAuthor(resultSet);
            }
            return Optional.ofNullable(author);
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }


    public List<Author> findAll(AuthorFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filter.getFirstName() != null) {
            whereSql.add(" first_name ILIKE ?");
            parameters.add("%" + filter.getFirstName() + "%");
        }
        if (filter.getLastname() != null) {
            whereSql.add(" last_name ILIKE ?");
            parameters.add("%" + filter.getLastname() + "%");
        }
        parameters.add(filter.getLimit());
        parameters.add(filter.getOffset());


        String where = whereSql.stream().collect(Collectors.joining(" AND ", " WHERE ", "LIMIT ? OFFSET ?"));
        String sql = whereSql.isEmpty() ? FIND_ALL_SQL + "LIMIT ? OFFSET ?"
                : FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = preparedStatement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                authors.add(buildAuthor(resultSet));
            }
            return authors;
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }

    public Optional<Author> findAuthorAndAllHisBooks(Long id) {
        String sql = FIND_AUTHOR_AND_BOOKS_SQL + " where authors.id = ?";
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Author author = new Author();
            List<Book> books = new ArrayList<>();
            if (resultSet.next()) {
                author = buildAuthor(resultSet);
                books.add(buildBook(resultSet));
            }
            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
            author.setBooks(books);
            return Optional.ofNullable(author);
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }


    private Author buildAuthor(ResultSet resultSet) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .birthDay(resultSet.getTimestamp("birth_day").toLocalDateTime().toLocalDate())
                .build();
    }


    private Book buildBook(ResultSet resultSet) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .genre(Genre.valueOf(resultSet.getString("genre")))
                .pageCount(resultSet.getInt("page_count"))
                .publishingYear(resultSet.getInt("publishing_year"))
                .build();
    }



            public static AuthorRepo getInstance() {
                return INSTANCE;
            }

}
