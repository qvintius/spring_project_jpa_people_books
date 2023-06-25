package mainpackage.dao;

import jakarta.persistence.EntityManager;
import mainpackage.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class PersonDAO {
    private final EntityManager em;

    @Autowired
    public PersonDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    public void testNPlusOne(){
        Session session = em.unwrap(Session.class);
        System.out.println("1");
        //1 запрос
        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
        //N запросов к бд
        for (Person person:people)
            System.out.println(person.getFullName() + " has " + person.getBooks());

        System.out.println("2");
        //Solution 1 запрос с left join
        List<Person> people1 = session.createQuery("select p from Person p LEFT JOIN FETCH p.books", Person.class).getResultList();
        for (Person person:people1)
            System.out.println(person.getFullName() + " has " + person.getBooks());
    }
}
