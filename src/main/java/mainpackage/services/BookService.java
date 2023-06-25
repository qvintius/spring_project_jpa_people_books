package mainpackage.services;

import mainpackage.models.Book;
import mainpackage.models.Person;
import mainpackage.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> findAll(){
        return bookRepo.findAll();
    }

    public Book findOne(int id){
        return bookRepo.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepo.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book boookToBeUpdated = bookRepo.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(boookToBeUpdated.getOwner());//не терять владельца при обновлении
        bookRepo.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepo.deleteById(id);
    }

    public Person getBookOwner(int id) {//null if book has no owner
        return bookRepo.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson){
        bookRepo.findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
        });

    }

    @Transactional
    public void release(int id){
        bookRepo.findById(id).ifPresent(
                book -> {
                 book.setOwner(null);
                });
    }

    public List<Book> searchByTitle(String query){
        return bookRepo.findByTitleStartingWith(query);
    }

}
