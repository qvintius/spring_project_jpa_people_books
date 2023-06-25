package mainpackage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Setter
    @Getter
    @Column(name = "full_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 100, message = "name should be from 2 to 100 characters")
    private String fullName;

    @Setter
    @Getter
    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "year should be greater than 1900")
    private int yearOfBirth;

    @Getter
    @Setter
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public void addBooks(Book book){
        if (this.books == null)
            this.books = new ArrayList<>();
        this.books.add(book);
        book.setOwner(this);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
