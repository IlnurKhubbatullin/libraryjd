package springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springmvc.dao.BookDAO;
import springmvc.dao.PersonDAO;
import springmvc.model.Book;
import springmvc.model.Person;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String findAllBooks(Model model) {
        model.addAttribute("books", bookDAO.findAllBooks());
        return "books/index_book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new_book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new_book";
        }

        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.findOneBook(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit_book";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String findOneBook(@PathVariable("id") int id, Model model) {
        searchAndAdd(id,model);
        return "books/show_book";
    }

    @PostMapping("/select")
    public String addBookInUser(@RequestParam Integer id, @RequestParam Integer book_id, Model model) {
        bookDAO.addBookInUser(id, book_id);
        return  "redirect:/books/" + book_id;
    }

    @PostMapping("/free")
    public String freeBook(@RequestParam Integer book_id, @RequestParam Integer person_id, Model model) {
        bookDAO.freeBook(book_id);
        return "redirect:/books/" + book_id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    public void searchAndAdd(int book_id, Model model) {
        Book book = bookDAO.findOneBook(book_id);
        Person person = bookDAO.seachPersonBook(book.getPersonId());
        List<Person> people = personDAO.findAllPersons();
        model.addAttribute("person", person);
        model.addAttribute("book", book);
        model.addAttribute("people",people);
    }
}
