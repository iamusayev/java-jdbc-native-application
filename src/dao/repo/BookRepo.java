package dao.repo;

import dao.entity.Book;
import exception.DaoException;
import model.enums.Genre;
import model.filter.BookFilter;
import pool.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static model.constants.ExceptionConstants.DAO_EXCEPTION_CODE;
import static model.constants.ExceptionConstants.DAO_EXCEPTION_MESSAGE;

public class BookRepo {

    private BookRepo() {}
    private static final BookRepo INSTANCE = new BookRepo();


    private static final String DELETE_SQL = """
                        
            DELETE FROM books 
                        
            """;


    private static final String FIND_ALL_SQL = """
                        
                        
            SELECT 
            id,
            name,
            genre,
            page_count,
            publishing_year
            FROM books
                        
                        
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?";

    private static final String ADD_BOOK_TO_AUTHOR_SQL = """
                        
            INSERT INTO books (author_id,name, genre, page_count, publishing_year)
            VALUES (?, ?, ?, ?, ?) 
                        
                        
            """;


    public void addBookToAuthor(Long id, Book book) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(ADD_BOOK_TO_AUTHOR_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getGenre().toString());
            preparedStatement.setInt(4, book.getPageCount());
            preparedStatement.setInt(5, book.getPublishingYear());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }

    public Optional<Book> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Book book = new Book();
            if (resultSet.next()) {
                book = buildBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }

    public List<Book> findAll(BookFilter bookFilter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (bookFilter.getName() != null) {
            whereSql.add(" name ILIKE ?");
            parameters.add("%" + bookFilter.getName() + "%");
        }
        if (bookFilter.getGenre() != null) {
            whereSql.add(" genre ILIKE ?");
            parameters.add("%" + bookFilter.getGenre() + "%");
        }
        parameters.add(bookFilter.getLimit());
        parameters.add(bookFilter.getOffset());

        String where = whereSql.stream().collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        String sql = whereSql.isEmpty() ? FIND_ALL_SQL + " LIMIT ? OFFSET ?" : FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql);) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = preparedStatement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
            return books;
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }

    public void deleteById(Long id) {
        String sql = DELETE_SQL + " where id = ?";
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
    }

    public void deleteBooksByAuthorId(Long authorId) {
        String sql = DELETE_SQL + " WHERE author_id = ?";
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, authorId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, DAO_EXCEPTION_CODE);
        }
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




            public static BookRepo getInstance() {
                return INSTANCE;
            }


}
