package springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private int id;
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2,max = 50,message = "Фамилия, имя и отчество должны быть не менее 5 символов и не более 50 символов")
    private String fullName;

    @Min(value = 1930,message = "Год рождения должен быть больше 1930 года")
    private int yearOfBirth;

}
