package Entities.SchoolOwned;

import Entities.Users.Student;
import Entities.Users.Teacher;
import com.github.javafaker.Faker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Courses")
public class Course implements Serializable, Comparable<Course> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;
    //list of teachers that teaches the course
    @ManyToMany
    @JoinTable(
    joinColumns = @JoinColumn(name = "CourseID"),
    inverseJoinColumns = @JoinColumn(name ="TeacherID"))
    private List<Teacher> teachers;

    @ManyToMany
    @JoinTable(
    joinColumns = @JoinColumn(name = "CourseID"),
    inverseJoinColumns = @JoinColumn(name = "StudentID"))
    private List<Student> students = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectID")
    private Subject subject = null;

    @ManyToMany(mappedBy = "courses")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private List<ExamForm> examForms = new ArrayList<>();
    @OneToMany(mappedBy = "course")
    private final List<ClassExam> classExams = new ArrayList<>();



    private String code;

    private int examFormsNumber = 0;
    private static int codeNum = 0;

    public Course()
    {
        code = Integer.toString(++codeNum);
        examFormsNumber = 0;
    }
    public Course(String Name,List<Teacher>TeacherList)
    {
        this.name=Name;
        this.teachers = TeacherList;
        code = Integer.toString(++codeNum);
        examFormsNumber = 0;
    }

    public Course(String name)
    {
        code = Integer.toString(++codeNum);
        examFormsNumber = 0;
    }


    public String getName(){return name;}
    public void setName(String newName){this.name=newName;}

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher)
    {
        if (!teachers.contains(teacher))
            teachers.add(teacher);
    }
    public List<Student> getStudents(){return students;}
    public void setStudents(List<Student> students){this.students =students;}
    
    public void addStudents(Student student)
    {
        if (!students.contains(student))
            students.add(student);
    }
    //public List<Question> getQuestions(){return questions;}
    public void setQuestions(List<Question> questions){this.questions = questions;}

    public void addQuestion(Question question)
    {
        if (!questions.contains(question))
            questions.add(question);
    }

    public List<ExamForm> getExamForms(){return examForms;}
    public void setExamForms(List<ExamForm> examForms){this.examForms = examForms;}

    public void addExamForm(ExamForm examForm)
    {
        if (!examForms.contains(examForm))
        {
            examForms.add(examForm);
            examFormsNumber++;
        }
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getCode() {
        if(code == null)
        {
            Faker faker = new Faker();
            code = faker.bothify("##");
        }
        return code;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public int getExamFormsNumber() {return this.examFormsNumber;}

    @Override
    public int compareTo(Course o) {
        return this.getName().compareTo(o.getName());
    }
}
