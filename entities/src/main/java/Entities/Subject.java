package Entities;

import com.github.javafaker.Faker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Subjects")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;
    @OneToMany(mappedBy = "subject")
    private List<Course> courses = new ArrayList<>();
    private String code;
    /*
    @OneToMany(mappedBy = "subject")
    private List<Entities.Question> questions = new ArrayList<>();
    */
    public Subject() {
    }

    public Subject(Long id, String name, List<Course> courses) {
        this.ID = id;
        this.name = name;
        this.courses = courses;
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(List<Course> courses) {
        this.courses = courses;
    }

    public void setId(Long id) {
        this.ID = id;
    }

    public Long getId() {
        return ID;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getCode() {
        if(code == null)
        {
            Faker faker = new Faker();
            code = faker.bothify("##");
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}