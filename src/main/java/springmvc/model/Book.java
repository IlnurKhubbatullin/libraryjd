package springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private int id;
    private int personId;
    @NotEmpty(message = "Название книги не может быть пустым")
    private String titleOfTheBook;
    @NotEmpty(message = "Имя автора не может быть пустым")
    private String author;

    @Min(value = 1900,message = "Год издания книги должен быть больше 1900 года")
    private int yearOfPublication;

}
