import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrincipleSidebarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private Label userTypeLabel;

    @FXML
    private Button viewClassExamsButton;

    @FXML
    private Button viewQuestionButton;

    @FXML
    private Button viewTestFormsButton;

    @FXML
    void addQuestion(ActionEvent event) {

    }

    @FXML
    void addTestForms(ActionEvent event) {

    }

    @FXML
    void gradeExam(ActionEvent event) {

    }

    @FXML
    void showStatistics(ActionEvent event) {

    }

    @FXML
    void viewClassExams(ActionEvent event) {

    }

    @FXML
    void viewQuestion(ActionEvent event) {

    }

    @FXML
    void viewTestForms(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert Menu != null : "fx:id=\"Menu\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert addQuestionButton != null : "fx:id=\"addQuestionButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert addTestFormsButton != null : "fx:id=\"addTestFormsButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert gradeExamButton != null : "fx:id=\"gradeExamButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert showStatisticsButton != null : "fx:id=\"showStatisticsButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert userTypeLabel != null : "fx:id=\"userTypeLabel\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert viewClassExamsButton != null : "fx:id=\"viewClassExamsButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert viewQuestionButton != null : "fx:id=\"viewQuestionButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";
        assert viewTestFormsButton != null : "fx:id=\"viewTestFormsButton\" was not injected: check your FXML file 'PrincipleSidebar.fxml'.";

        userTypeLabel.setText("Logged in as: Principal");
    }

}