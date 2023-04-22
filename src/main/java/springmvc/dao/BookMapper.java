package springmvc.dao;

import org.springframework.jdbc.core.RowMapper;

import springmvc.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper  implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setPersonId(resultSet.getInt("personId"));
        book.setTitleOfTheBook(resultSet.getString("titleOfTheBook"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearOfPublication(resultSet.getInt("yearOfPublication"));

        return book;
    }
}
