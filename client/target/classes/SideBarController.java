import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class SideBarController {

    @FXML
    private Label Menu;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button addTestFormsButton;

    @FXML
    private Button gradeExamButton;

    @FXML
    private Button showStatisticsButton;

    @FXML
    private Button viewQuestionButton;

    @FXML
    private Button viewTestFormsButton;

    @FXML
    private Button viewClassExamsButton;

    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("AddQuestion");

    }

    @FXML
    void addTestForms(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("ViewTestForms");
    }

    @FXML
    void gradeExam(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("GradeExams");
    }


    @FXML
    void showStatistics(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("ShowStatistics");
    }

    @FXML
    void viewQuestion(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("ViewQuestions");
    }

    @FXML
    void viewClassExams(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("ViewClassExams");
    }

    @FXML
    void viewTestForms(ActionEvent event) throws IOException {
        SimpleChatClient.getMainWindowController().LoadSceneToMainWindow("ViewTestForms");
    }

    @FXML
    void initialize() {
        InitializationAsserts();
    }

    void InitializationAsserts(){
        assert Menu != null : "fx:id=\"Menu\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert addQuestionButton != null : "fx:id=\"addQuestionButton\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert addTestFormsButton != null : "fx:id=\"addTestFormsButton\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert gradeExamButton != null : "fx:id=\"gradeExamButton\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert showStatisticsButton != null : "fx:id=\"showStatisticsButton\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert viewQuestionButton != null : "fx:id=\"viewQuestionButton\" was not injected: check your FXML file 'SideBar.fxml'.";
        assert viewTestFormsButton != null : "fx:id=\"viewTestFormsButton\" was not injected: check your FXML file 'SideBar.fxml'.";

    }

}
