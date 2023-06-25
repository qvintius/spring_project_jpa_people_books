package mainpackage.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Setter
    @Getter
    @Column(name = "title")
    @NotEmpty(message = "title shouldn't be empty")
    @Size(min = 2, max = 100, message = "title should be from 2 to 100 characters")
    private String title;

    @Setter
    @Getter
    @Column(name = "author")
    @NotEmpty(message = "author shouldn't be empty")
    @Size(min = 2, max = 100, message = "author should be from 2 to 100 characters")
    private String author;

    @Setter
    @Getter
    @Column(name = "year")
    @Min(value = 1100, message = "year should be greater than 1900")
    private int year;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
