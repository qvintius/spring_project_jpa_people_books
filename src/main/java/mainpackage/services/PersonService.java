package mainpackage.services;

import mainpackage.models.Book;
import mainpackage.models.Person;
import mainpackage.repositories.PersonRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public List<Person> findAll(){
        return personRepo.findAll();
    }

    public Person findById(int id){
        Optional<Person> foundedPerson = personRepo.findById(id);
        return foundedPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        personRepo.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepo.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        personRepo.deleteById(id);
    }

    public Optional<Person> getPersonByFullName(String name){
        return personRepo.findByFullName(name);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = personRepo.findById(id);
        Hibernate.initialize(person.get().getBooks());
        if (person.isPresent())
            return person.get().getBooks();
        else
            return Collections.emptyList();
    }


}
