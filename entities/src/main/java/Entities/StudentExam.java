package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "StudentExams")
public class StudentExam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Student")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClassExam")
    private ClassExam classExam;

    @ElementCollection
    private List<Integer> studentAnswers = new ArrayList<>();

    // Switch to Grade class? Delete Grade Class?
    private int grade;

    public enum statusEnum {NotTaken, ToEvaluate, Approved, Disapproved}

    private statusEnum status;

    private double timeLeft;

    private String teacherNote;
    private String scoreChangeReason;

    public StudentExam() {
    }

    public StudentExam(Student student, ClassExam classExam, List<Integer> studentAnswers, int grade, statusEnum status)
    {
        this.student = student;
        this.student.AddStudentExam(this);
        this.classExam = classExam;
        this.classExam.AddStudentExam(this);
        this.timeLeft = this.classExam.getExamForm().getExamTime();
        this.studentAnswers = studentAnswers;
        this.grade = grade;
        this.status = status;
    }

    public void update(StudentExam other)
    {
        this.grade = other.grade;
        this.studentAnswers = new ArrayList<>(other.getStudentAnswers());
        this.status = other.status;
    }

    public int getID()
    {
        return this.ID;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public ClassExam getClassExam()
    {
        return classExam;
    }

    public void setClassExam(ClassExam classExam)
    {
        this.classExam = classExam;
    }

    public List<Integer> getStudentAnswers()
    {
        return studentAnswers;
    }

    public void setStudentAnswers(List<Integer> studentAnswers)
    {
        this.studentAnswers = studentAnswers;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public statusEnum getStatus()
    {
        return status;
    }

    public void setStatus(statusEnum status)
    {
        this.status = status;
    }
    public String TranslateStatus()
    {
        switch (status)
        {
            case Approved -> {
            return "Approved";
        }
            case ToEvaluate -> {
            return "To Evaluate";
        }
            case NotTaken -> {
                return "Not Taken";
            }
            case Disapproved -> {
                return "Disapproved";
            }
        }
        return "";
    }

    public double getTimeLeft(){return timeLeft;}
    public void setTimeLeft(double timeLeft){this.timeLeft=timeLeft;}

    public String getTeacherNote()
    {
        return teacherNote;
    }

    public void setTeacherNote(String teacherNote)
    {
        this.teacherNote = teacherNote;
    }

    public String getScoreChangeReason()
    {
        return scoreChangeReason;
    }

    public void setScoreChangeReason(String scoreChangeReason)
    {
        this.scoreChangeReason = scoreChangeReason;
    }


}
