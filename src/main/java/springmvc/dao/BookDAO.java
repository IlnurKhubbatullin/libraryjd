package springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springmvc.model.Book;
import springmvc.model.Person;

import java.util.List;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> findAllBooks(){
        return jdbcTemplate.query("select * from book order by id",new BookMapper());
    }
    public Book findOneBook(int id){
        return jdbcTemplate.query("select * from book where id = ?",new Object[]{id},new BookMapper()).
                stream().findAny().orElse(null);
    }
    public void saveBook(Book book){
        jdbcTemplate.update("insert into book (titleOfTheBook,author,yearOfPublication) values (?,?,?)",
                book.getTitleOfTheBook(),book.getAuthor(),book.getYearOfPublication());
    }
    public void updateBook(int id, Book updateBook){
        jdbcTemplate.update("update book set titleOfTheBook = ?,author = ?, yearOfPublication = ? where id = ?",
                updateBook.getTitleOfTheBook(),updateBook.getAuthor(),updateBook.getYearOfPublication(),id);
    }
    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }

    public void addBookInUser(int personId,int id) {
        jdbcTemplate.update("update book set personId = ? where id = ?",
                personId, id);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("update book set id = ? where id = ?",null, id);
    }

    public Person seachPersonBook(int personId){
        return jdbcTemplate.query("select * from person where id = ?",new Object[]{personId},new PersonMapper()).
                stream().findAny().orElse(null);
    }
}
