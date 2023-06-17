package Client.Controllers.MainViews.StaffViews.TeacherViews;

import Client.Controllers.MainViews.SaveBeforeExit;
import Client.Controllers.MainViews.ViewExamController;
import Client.Events.*;
import Client.SimpleChatClient;
import Client.SimpleClient;
import Entities.Communication.Message;
import Entities.SchoolOwned.*;
import Entities.Users.Teacher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class TeacherAddTestFormController extends SaveBeforeExit {
    private int examID;
    private int examTime = 0;
    private Teacher teacher;
    private Subject chosenSubject = null;
    private Course chosenCourse = null;
    private String headerText;
    private String footerText;
    private String examNotesForStudent;
    private String examNotesForTeacher;
    private String examName; // necessary?
    private List<QuestionObject> questionObjectsList;
    private final List<Question> addedQuestions = new ArrayList<>();

    private List<Subject> teacherSubjects;
    @FXML
    private int msgId;
    @FXML
    private ComboBox<Course> CourseCB;

    @FXML
    private ComboBox<Subject> SubjectCB;

    @FXML
    private Button addNotesForStudentButton;

    @FXML
    private Button addNotesForTeacherButton;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button previewTestButton;

    @FXML
    private Button saveTestButton;

    @FXML
    private Button setTimeButton;

    @FXML
    private Button resetButton;

    @FXML
    private TextArea footerTextTF;

    @FXML
    private TextArea headerTextTF;

    @FXML
    private TableView<QuestionObject> questionTable;

    @FXML
    private TableColumn<QuestionObject, Integer> gradePercentageColumn;

    @FXML
    private TableColumn<QuestionObject, Integer> questionIdColumn;

    @FXML
    private TableColumn<QuestionObject, String> questionTextColumn;

    /*
    @FXML
    void rowClicked(MouseEvent event) {
        System.out.println("row clicked");
        Client.Controllers.MainViews.TeacherViews.TeacherAddTestFormController.QuestionObject q = questionTable.getSelectionModel().getSelectedItem();
        VBoxGrade.setText(String.valueOf(q.getGradePercentage()));
        VBoxQuestionID.setText(String.valueOf(q.getQuestion().getId()));
        VBoxQuestionText.setText(q.getQuestion().getQuestionText());

    }*/

    @FXML
    void setTime(ActionEvent event) {
        // open new input dialog for time
        TextInputDialog dialog = new TextInputDialog();
        dialog.getDialogPane().setMinWidth(400);
        dialog.getDialogPane().setMinHeight(200);
        dialog.setTitle("Set time");
        dialog.setHeaderText("Set time for exam (in minutes)");
        dialog.setContentText("Time:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // check if input is valid
            if (result.get().matches("[0-9]+")) {
                int time = Integer.parseInt(result.get());
                if (time > 0 && time < 180) {
                    // set time
                    setTimeButton.setText("Exam time: " + time + " minutes");
                    examTime = time;
                }
                else {
                    // invalid input
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Please enter a number between 1 and 180 minutes");
                    alert.showAndWait();
                }
            }
        }
    }


    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        // switch to TeacherViewQuestions
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("TeacherViewQuestions");

        // send info to TeacherViewQuestions
        ChooseQuestionsEvent stMsg = new ChooseQuestionsEvent();
        stMsg.setCourse(CourseCB.getValue());
        stMsg.setSubject(SubjectCB.getValue());
        stMsg.setQuestions(addedQuestions);
        System.out.println("Course: " + CourseCB.getValue());
        EventBus.getDefault().post(stMsg);
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void setQuestions(SendChosenQuestionsEvent event) {
        Platform.runLater(()-> {
            try{
                System.out.println("received questions from TeacherViewQuestions: " + event.getQuestions());
                chosenSubject= event.getSubject();
                chosenCourse= event.getCourse();
                //SubjectCB.getItems().clear();
                //CourseCB.getItems().clear();
                //SubjectCB.getItems().add(chosenSubject);
                //CourseCB.getItems().add(chosenCourse);
                SubjectCB.setValue(chosenSubject);
                CourseCB.setValue(chosenCourse);
                SubjectCB.setDisable(true);
                CourseCB.setDisable(true);
                resetButton.setVisible(true);
                resetButton.setDisable(false);
                enable();
                addQuestionButton.setDisable(false);



                //List<Question> addedQuestions = event.getQuestions();
                for (Question q : event.getQuestions()) { // add questions to addedQuestions list if they are not already there
                    if(!addedQuestions.contains(q))
                        addedQuestions.add(q);
                }
                questionObjectsList.clear();
                for (Question q : addedQuestions) { // convert questions to questionTable
                    QuestionObject newQuestion = new QuestionObject(q.getID(), q.getQuestionData(), 0);
                    questionObjectsList.add(newQuestion);
                }
                questionTable.getItems().clear();
                questionTable.getItems().addAll(questionObjectsList);
                questionTable.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });}

    @FXML
    void previewTest(ActionEvent event) throws IOException {
        // open new word document with the test
        prepareExamFormForPreview();
    }

    @FXML
    void saveTest(ActionEvent event) throws IOException {
        int sum = 0;
        for (QuestionObject qo : questionObjectsList) {
            sum += qo.getPercentage();
        }
        if (sum!= 100)
        {
            // show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("The sum of the grades must be 100");
            alert.showAndWait();
            return;
        }
        if (examTime<0 || examTime>180)
        {
            // show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please set the time for the exam");
            alert.showAndWait();
            return;
        }
        teacher = ((Teacher)(SimpleClient.getClient().getUser()));
        headerText= headerTextTF.getText();
        footerText= footerTextTF.getText();
        Date createdDate = new Date();
        List<Integer> grades = new ArrayList<>();
        for (Question q : addedQuestions) {
            for (QuestionObject qo : questionObjectsList) {
                if (q.getID() == qo.getQuestionId()) {
                    grades.add(qo.getPercentage());
                    break;
                }
            }
        }
        ExamForm examForm = new ExamForm(teacher, chosenSubject, chosenCourse, addedQuestions, grades, createdDate, headerText, footerText, examNotesForTeacher, examNotesForStudent, examTime);
        Message message = new Message(1, "Add ExamForm: " + "Subject-" + chosenSubject + ", Course-" + chosenCourse);
        message.setData(examForm);
        SimpleClient.getClient().sendToServer(message);
    }

    void prepareExamFormForPreview() throws IOException {
        teacher = ((Teacher)(SimpleClient.getClient().getUser()));
        headerText= headerTextTF.getText();
        footerText= footerTextTF.getText();
        Date createdDate = new Date();
        ExamForm examForm = new ExamForm(teacher, chosenSubject, chosenCourse, addedQuestions, createdDate, headerText, footerText, examNotesForTeacher, examNotesForStudent);
        ClassExam classExam = new ClassExam(examForm);
        ViewExamController.createManualExam(classExam);
    }

    @Subscribe
    public void examSaved(GeneralEvent event) throws IOException {
        Platform.runLater(()-> {
            try{
                System.out.println("exam saved");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exam Saved");
                alert.setHeaderText("Exam Saved");
                alert.setContentText("Exam Saved Successfully");
                alert.showAndWait();
                //resetForm(null);
                EventBus.getDefault().unregister(this);
                SimpleChatClient.setRoot("TeacherMainScreen");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });}

    @FXML
    void resetForm(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Are you sure you want to reset the form?");
        alert.setContentText("All the data you have entered will be lost");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            resetButton.setVisible(false);
            //SubjectCB.getSelectionModel().clearSelection();
            //CourseCB.getSelectionModel().clearSelection();
            headerTextTF.clear();
            footerTextTF.clear();
            questionTable.getItems().clear();
            questionTable.refresh();
            addedQuestions.clear();
            questionObjectsList.clear();
            disable();
            SubjectCB.getItems().clear();
            CourseCB.getItems().clear();
            SubjectCB.setDisable(false);
            Message message = new Message(1, "1Get Subjects of Teacher: " + SimpleClient.getClient().getUser().getID());
            SimpleClient.getClient().sendToServer(message);
        }
        else {
        }
    }

    @Subscribe
    public void updateSubjects(SubjectsOfTeacherMessageEvent eventSUB) throws IOException {
        resetButton.setVisible(false);
        teacher = (Teacher) SimpleClient.getClient().getUser();
        teacherSubjects=eventSUB.getSubjects();
        if (teacherSubjects != null){
            Collections.sort(teacherSubjects);
            SubjectCB.getItems().addAll(teacherSubjects);
        }
        else {
            JOptionPane.showMessageDialog(null, "Could not Retrieve any subjects", "DataBase Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        disable();
        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        gradePercentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        questionTable.setEditable(true);
        questionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        questionObjectsList = new ArrayList<>();
        Message message = new Message(1, "1Get Subjects of Teacher: " + SimpleClient.getClient().getUser().getID());
        SimpleClient.getClient().sendToServer(message);
    }


    @FXML // activated when user selects a Subject
    public void onSubjectSelection(ActionEvent event){
        if (SubjectCB.getSelectionModel().getSelectedItem() == null){
        }
        else{
            List<Course> courses = SubjectCB.getSelectionModel().getSelectedItem().getCourses();
            if (courses != null){
                chosenSubject= SubjectCB.getSelectionModel().getSelectedItem(); // save the chosen subject
                SubjectCB.setDisable(true);
                Collections.sort(courses);
                CourseCB.getItems().addAll(courses);
                CourseCB.setDisable(false);
                resetButton.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Could not Retrieve any courses", "DataBase Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @FXML // activated when user selects a Course
    public void onCourseSelection(ActionEvent event) throws IOException {
        if (CourseCB.getSelectionModel().getSelectedItem() == null){
        }
        else{
            chosenCourse = CourseCB.getSelectionModel().getSelectedItem(); // save the chosen course
            CourseCB.setDisable(true);
            Message message= new Message(++msgId, "Get Questions for Course: " + CourseCB.getSelectionModel().getSelectedItem());
            message.setData(CourseCB.getSelectionModel().getSelectedItem());
            SimpleClient.getClient().sendToServer(message);
        }
    }

    @FXML // activated when user clicks on a row in the table of questions, opens a dialog to edit the grade percentage
    void rowClicked(MouseEvent event) {
        if (questionTable.getSelectionModel().getSelectedItem() == null){
        }
        else{
            QuestionObject questionObject = questionTable.getSelectionModel().getSelectedItem();
            int index = questionTable.getSelectionModel().getSelectedIndex();
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Grade Percentage");
            dialog.setHeaderText("Edit Grade Percentage");
            dialog.setContentText("Please enter the new grade percentage:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    int newGradePercentage = Integer.parseInt(result.get());
                    if (newGradePercentage < 0 || newGradePercentage > 100){
                        JOptionPane.showMessageDialog(null, "Grade Percentage must be between 0 and 100", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        questionObject.setPercentage(newGradePercentage);
                        questionObjectsList.set(index, questionObject);
                        questionTable.getItems().clear();
                        questionTable.getItems().addAll(questionObjectsList);
                        questionTable.refresh();
                    }
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Grade Percentage must be a number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }


    @Subscribe
    public void updateScreen(CourseQuestionsListEvent event) {
        CourseCB.setDisable(true);
        questionTable.setDisable(false);
        addQuestionButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        /*List<Client.Controllers.MainViews.TeacherViews.TeacherAddTestFormController.QuestionObject> questionObjectsList = new ArrayList<>();
        for (Question question : event.getQuestions()) {
            //System.out.println(question.getQuestionData());
            Client.Controllers.MainViews.TeacherViews.TeacherAddTestFormController.QuestionObject item = new Client.Controllers.MainViews.TeacherViews.TeacherAddTestFormController.QuestionObject(question.getID(), question.getQuestionData(), 0);
            questionObjectsList.add(item);
        }
        if (questionObjectsList != null) {
            questionTable.getItems().clear();
            questionTable.getItems().addAll(questionObjectsList);
            questionTable.refresh();
        }*/
        //enable();
        addQuestionButton.setDisable(false);
    }


//////////////////////////// diasble and enable buttons ////////////////////////////
    void disable(){
        CourseCB.setDisable(true);
        headerTextTF.setDisable(true);
        footerTextTF.setDisable(true);
        //questionTable.setDisable(true);
        addQuestionButton.setDisable(true);
        addNotesForStudentButton.setDisable(true);
        addNotesForTeacherButton.setDisable(true);
        previewTestButton.setDisable(true);
        saveTestButton.setDisable(true);
        setTimeButton.setDisable(true);
    }

    void enable(){
        headerTextTF.setDisable(false);
        footerTextTF.setDisable(false);
        //questionTable.setDisable(false);
        //addQuestionButton.setDisable(false);
        addNotesForStudentButton.setDisable(false);
        addNotesForTeacherButton.setDisable(false);
        previewTestButton.setDisable(false);
        saveTestButton.setDisable(false);
        setTimeButton.setDisable(false);

    }

/////////////////////// add notes buttons ///////////////////////
    @FXML
    void addNotesForStudent(ActionEvent event) {
        // open new input dialog for notes for student
        TextInputDialog dialog = new TextInputDialog();
        dialog.getDialogPane().setMinWidth(400);
        dialog.getDialogPane().setMinHeight(200);
        dialog.setTitle("Notes for student");
        dialog.setHeaderText("Add notes for student");
        dialog.setContentText("Notes:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            examNotesForStudent = result.get();
        }
    }

    @FXML
    void addNotesForTeacher(ActionEvent event) {
        // open new input dialog for notes for student
        TextInputDialog dialog = new TextInputDialog();
        dialog.getDialogPane().setMinWidth(400);
        dialog.getDialogPane().setMinHeight(200);
        dialog.setTitle("Notes for teacher");
        dialog.setHeaderText("Add notes for teacher");
        dialog.setContentText("Notes:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            examNotesForTeacher = result.get();
        }
    }
///////////////////// SaveBeforeExit ////////////////////////
    @Override
    public boolean CheckForUnsavedData() {
        System.out.println("CheckForUnsavedData Client.Controllers.MainPanelScreens.SaveBeforeExit");
        if (chosenSubject != null && chosenCourse != null && addedQuestions != null && !addedQuestions.isEmpty() && examTime != 0) {
            int sum = 0;
            for (QuestionObject qo : questionObjectsList) {
                sum += qo.getPercentage();
            }
            if (sum == 100)
            {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void SaveData() {
        ActionEvent event = new ActionEvent();
        try {
            saveTest(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////
    // class for the table of questions
    public static class QuestionObject {
        private int questionId;
        private String question;
        private int percentage;

        public QuestionObject(){}
        public QuestionObject (int questionId, String question, int percentage){
            this.questionId = questionId;
            this.question = question;
            this.percentage = percentage;
        }

        public QuestionObject (int questionId, String question){
            this.questionId = questionId;
            this.question = question;
            this.percentage = 0;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getQuestion() {
            return question;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }


        @Override
        public String toString() {
            return "Client.Controllers.MainViews.TeacherViews.TeacherAddTestFormController.QuestionObject{" +
                    "question='" + question + '\'' +
                    '}';
        }
    }
}