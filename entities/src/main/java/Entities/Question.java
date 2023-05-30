package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="Questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //////////////////////////////////////////////////////
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    joinColumns = @JoinColumn(name = "QuestionID"),
    inverseJoinColumns = @JoinColumn(name ="CourseID"))
    private List<Course> courses = new ArrayList<>();
    //@ManyToOne
    //@JoinColumn(name = "courseID")
    //private Course course;
    //////////////////////////////////////////////////////

    private String questionData;
    @ElementCollection
    private List<String> Answers;
    private String correctAnswer;
    private String teacherNote;
    private String studentNote;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name ="Question_ID" ),
            inverseJoinColumns = @JoinColumn(name = "ExamForm_ID"))
    private List<ExamForm> examForms = new ArrayList<>();

    //@ManyToOne
    //private Subject subject;

    public Question(){}

    //////////////////////////////////////////////////////
    public Question(List<Course> courses, String questionData,List<String>Answer, String correctAnswer,String teacherNote,String studentNote)
    {
        this.courses=courses;
        this.questionData=questionData;
        this.Answers=Answer;
        this.correctAnswer=correctAnswer;
        this.teacherNote=teacherNote;
        this.studentNote=studentNote;
    }
    //public Question(Course course, String questionData,List<String>Answer, String correctAnswer,String teacherNote,String studentNote)
    //{
    //    this.course=course;
    //    this.questionData=questionData;
    //    this.Answers=Answer;
    //    this.correctAnswer=correctAnswer;
    //    this.teacherNote=teacherNote;
    //    this.studentNote=studentNote;
    //}
    //////////////////////////////////////////////////////

    public Question(String questionData, String correctAnswer, String teacherNote, String studentNote) {
        this.questionData = questionData;
        this.correctAnswer = correctAnswer;
        this.teacherNote = teacherNote;
        this.studentNote = studentNote;
    }

    //////////////////////////////////////////////////////
    //public Course getCourse(){return course;}
    //public void setCourse(Course newCourse){this.course=newCourse;}
    public List<Course> getCourses(){return courses;}
    public void setCourses(List<Course> courses)
    {
        if (this.courses != courses)
            this.courses = new ArrayList<>(courses);
    }
    public void AddsetCourse(Course course)
    {
        if(!this.courses.contains(course))
            courses.add(course);
    }
    //////////////////////////////////////////////////////
    public String getQuestionData(){return questionData;}
    public void setQuestionData(String questionData){this.questionData=questionData;}
    public List<String> getAnswers(){return Answers;}
    public void setAnswers(List<String> newAnswers){this.Answers=newAnswers;}
    public String getCorrectAnswer(){return correctAnswer;}
    public void setCorrectAnswer(String  correctAnswer){this.correctAnswer=correctAnswer;}
    public  String getTeacherNote(){return teacherNote;}
    public void setTeacherNote(String newNote){this.teacherNote=newNote;}
    public String getStudentNote(){return studentNote;}
    public void setStudentNote(String newNote){this.studentNote=newNote;}

}
