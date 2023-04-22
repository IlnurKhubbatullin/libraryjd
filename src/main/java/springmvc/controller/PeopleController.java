package springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springmvc.dao.PersonDAO;
import springmvc.model.Person;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String findAllPersons(Model model){
        List<Person> people = personDAO.findAllPersons();
        model.addAttribute("people",people);
        return "people/index_person";
    }
    @GetMapping("/{id}")
    public String findOnePerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.findOnePerson(id));
        model.addAttribute("user_books",personDAO.findUserBooks(id));
        return "people/show_person";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person){
        return "people/new_person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "people/new_person";
        }

        personDAO.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person",personDAO.findOnePerson(id));
        return "people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,@PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "people/edit_person";
        }
        personDAO.updatePerson(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

}
