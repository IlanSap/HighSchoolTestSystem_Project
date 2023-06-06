import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Entities.*;
import Events.CoursesOfTeacherEvent;
import Events.ExamMessageEvent;
import Events.SubjectsOfTeacherMessageEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;

public class TeacherCreateClassExamController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker endDateTF;

    @FXML
    private TableView<ClassExam> ExamFormsTV;

    @FXML
    private TextField codeTF;

    @FXML
    private ComboBox<Course> courseCB;

    @FXML
    private TextField endTimeTF;

    @FXML
    private TextField examTimeTF;

    @FXML
    private Button saveExamButton;

    @FXML
    private DatePicker startDateTF;

    @FXML
    private TextField startTimeTF;

    @FXML
    private ComboBox<Subject> subjectCB;


    private List<Course> courses;
    private ClassExam classExam;



    @FXML
    void initialize() throws IOException {
        assert endDateTF != null : "fx:id=\"EndDateTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert ExamFormsTV != null : "fx:id=\"ExamFormsTV\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert codeTF != null : "fx:id=\"codeTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert courseCB != null : "fx:id=\"courseCB\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert endTimeTF != null : "fx:id=\"endTimeTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert examTimeTF != null : "fx:id=\"examTimeTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert saveExamButton != null : "fx:id=\"saveExamButton\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert startDateTF != null : "fx:id=\"startDateTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert startTimeTF != null : "fx:id=\"startTimeTF\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        assert subjectCB != null : "fx:id=\"subjectCB\" was not injected: check your FXML file 'TeacherCreateClassExam.fxml'.";
        EventBus.getDefault().register(this);
        ExamFormsTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        courseCB.setDisable(true);
        ExamFormsTV.setDisable(true);
        startDateTF.setDisable(true);
        startTimeTF.setDisable(true);
        endTimeTF.setDisable(true);
        endDateTF.setDisable(true);
        codeTF.setDisable(true);
        examTimeTF.setDisable(true);
        Message subjectMessage = new Message(1, "1Get Subjects of Teacher: " + ((Teacher)(SimpleClient.getUser())).getID());
        Message courseMessage = new Message(1, "1Get Courses of Teacher: " + ((Teacher)(SimpleClient.getUser())).getID());
        subjectMessage.setData(SimpleClient.getUser());
        courseMessage.setData(SimpleClient.getUser());
         SimpleClient.getClient().sendToServer(subjectMessage);
        SimpleClient.getClient().sendToServer(courseMessage);
        classExam = new ClassExam();;
    }

    @FXML
    public void onSubjectSelection()
    {
        System.out.println("Subject Selected");
        Subject selectedSubject = subjectCB.getSelectionModel().getSelectedItem();
        if(courses != null /*&& !courses.isEmpty()*/)
        {
            for(Course course: courses) {
                if(course.getSubject() == selectedSubject) {
                    courseCB.getItems().add(course);
                }
            }
            courseCB.setDisable(false);

        }
        else
        {
            JOptionPane.showMessageDialog(null, "You do not teach in any subjects or the database could not retrieve the data", "Database Error", JOptionPane.WARNING_MESSAGE);
        }
        classExam.setSubject(subjectCB.getSelectionModel().getSelectedItem());

    }

    @FXML
    public void onCourseSelection() throws IOException {
        Message message = new Message(1, "Get Exams For Course: " + courseCB.getSelectionModel().getSelectedItem().getName());
        message.setData(courseCB.getSelectionModel().getSelectedItem());
        SimpleClient.getClient().sendToServer(message);

        classExam.setCourse(courseCB.getSelectionModel().getSelectedItem());
    }
    @FXML
    public void onExamFormChosen()
    {
        startDateTF.setDisable(false);
        startTimeTF.setDisable(false);
        endTimeTF.setDisable(false);
        endDateTF.setDisable(false);
        codeTF.setDisable(false);
        examTimeTF.setDisable(false);
    }
    @FXML
    public void onSaveExam()
    {
        JOptionPane.showMessageDialog(null,startDateTF.getChronology(),"Test", JOptionPane.WARNING_MESSAGE);
    }

    @Subscribe
    public void displayExamForms(ExamMessageEvent event)
    {
        List<ClassExam> exams = event.getClassExams();
        if(exams != null && exams.isEmpty())
        {
            ExamFormsTV.getItems().addAll(exams);
            ExamFormsTV.setDisable(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "There are no exam forms in this course, please create some", "Database Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    @Subscribe
    public void displaySubjects(SubjectsOfTeacherMessageEvent event) throws IOException {
        List<Subject> subjects = event.getSubjects();
        if(subjects != null && !subjects.isEmpty())
        {
            subjectCB.getItems().addAll(subjects);

        }
        else
        {
            JOptionPane.showMessageDialog(null, "You do not teach in any subjects or the database could not retrieve the data", "Database Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    @Subscribe
    public void displayCourses(CoursesOfTeacherEvent event) throws IOException {
       courses = event.getCourses();
        System.out.println("Courses: " + courses);

    }
    public static boolean isValidTimeFormat(String input) {
        String pattern = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
        return Pattern.matches(pattern, input);
    }
    public static boolean isValidDate(String input) {
        return true;

    }




}