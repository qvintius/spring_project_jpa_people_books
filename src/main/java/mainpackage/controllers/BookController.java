package mainpackage.controllers;

import jakarta.validation.Valid;
import mainpackage.dao.BookDAO;
import mainpackage.dao.PersonDAO;
import mainpackage.models.Book;
import mainpackage.models.Person;
import mainpackage.services.BookService;
import mainpackage.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, PersonService personService, BookService bookService) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String allBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "/books/all";
    }

    @GetMapping("/{id}")
    public String idBook(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){
        model.addAttribute("book", bookService.findOne(id));
        Person bookOwner = bookService.getBookOwner(id);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", personService.findAll());
        return "/books/idBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping("")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "/books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }


    @GetMapping("/search")
    public String searchPage(){
        return "/books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchByTitle(query));
        return "/books/search";
    }

}