package Entities.Users;

import Entities.Communication.ExtraTime;
import Entities.Enums;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@Table(name = "Person")
@DiscriminatorColumn(name="person_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Person implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String firstName;
    private String lastName;
    private Enums.Gender gender;
    private String email;
    private String password;

    public Person(){}

    public Person(String firstName, String lastName, Enums.Gender gender, String email, String password)  {
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
    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    public int getID() {
        return ID;
    }

    public void setPersonID(int personID) {
        this.ID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
    	return firstName + " " + lastName;
    }

    public Enums.Gender getGender() {
        return gender;
    }

    public void setGender(Enums.Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void extraTimeRequest(ExtraTime data);

    public abstract void receiveExtraTime(ExtraTime data);


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getEmail(), person.getEmail()) && Objects.equals(getPassword(), person.getPassword());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
