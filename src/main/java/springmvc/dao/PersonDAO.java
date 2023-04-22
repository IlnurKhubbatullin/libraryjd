package springmvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springmvc.model.Book;
import springmvc.model.Person;

import java.util.List;

@Component
public class PersonDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person>  findAllPersons(){
        return jdbcTemplate.query("select * from person order by id",new PersonMapper());
    }
    public Person findOnePerson(int id){
        return jdbcTemplate.query("select * from person where id = ?",new Object[]{id},new PersonMapper()).
        stream().findAny().orElse(null);
    }
    public void savePerson(Person person){
        jdbcTemplate.update("insert into person(fullName,yearOfBirth) values (?,?)",
                person.getFullName(),person.getYearOfBirth());
    }
    public void updatePerson(int id, Person updatePerson){
        jdbcTemplate.update("update person set fullName = ?,yearOfBirth = ? where id = ?",
                updatePerson.getFullName(),updatePerson.getYearOfBirth(),id);
    }
    public void deletePerson(int id){
        jdbcTemplate.update("delete from person where id = ?",id);
    }

    public List<Book> findUserBooks(int id) {
        return jdbcTemplate.query("select * from book where id = ? order by id",new Object[]{id},new BookMapper());
    }

}
