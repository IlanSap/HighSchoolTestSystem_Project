package Entities;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
@Table(name = "Entities.Person")
@DiscriminatorColumn(name="person_type", discriminatorType = DiscriminatorType.INTEGER)
public class Person implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;

    public Person(){}

    public Person(String firstName, String lastName, Gender gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getID() {
        return ID;
    }

    public void setPersonID(long personID) {
        this.ID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public String getFullName() {
    	return firstName + " " + lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }
}