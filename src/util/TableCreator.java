package util;

import pool.ConnectionManager;

import java.sql.Connection;
import java.sql.Statement;

public class TableCreator {

    private static final String CREATE_AUTHORS = """
                        
            CREATE TABLE authors
            (
                id         SERIAL PRIMARY KEY ,
                first_name VARCHAR(256) NOT NULL,
                last_name  VARCHAR(256) NOT NULL,
                birth_day  DATE         NOT NULL                
            
            );
                        
                        
            """;

    private static final String CREATE_BOOKS = """
            
            CREATE TABLE books
            (
                id              SERIAL PRIMARY KEY,
                name            VARCHAR(256) NOT NULL  UNIQUE ,
                genre           VARCHAR(64)  NOT NULL,
                page_count      INTEGER      NOT NULL,
                publishing_year INTEGER      NOT NULL,
                author_id       BIGINT       REFERENCES authors(id)
            )
            
            """;




    public static void createBooksAndAuthorsTables() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            statement  = connection.createStatement();
            statement.addBatch(CREATE_AUTHORS);
            statement.addBatch(CREATE_BOOKS);
            statement.executeBatch();
            connection.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
