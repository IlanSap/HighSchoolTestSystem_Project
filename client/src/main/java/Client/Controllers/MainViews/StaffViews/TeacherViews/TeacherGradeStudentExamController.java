package Client.Controllers.MainViews.StaffViews.TeacherViews;

import Client.Controllers.MainViews.SaveBeforeExit;
import Client.Controllers.MainViews.StaffViews.ShowStatisticsController;
import Client.Events.ClassExamGradeEvent;
import Client.Events.RefreshPerson;
import Client.Events.StudentExamEvent;
import Client.SimpleChatClient;
import Client.SimpleClient;
import Entities.Communication.Message;
import Entities.Enums;
import Entities.SchoolOwned.Question;
import Entities.StudentOwned.StudentExam;
import Entities.Users.Person;
import Entities.Users.Principal;
import Entities.Users.Student;
import Entities.Users.Teacher;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TeacherGradeStudentExamController extends SaveBeforeExit
{
    @FXML
    private VBox AnswersVBOX;

    @FXML
    private Button ApproveBtn;
    @FXML
    private Button DisapproveBtn;
    @FXML
    private Button BackBtn;
    @FXML
    private HBox ChangeScoreHbox;
    @FXML
    private HBox ButtonsHbox;

    @FXML
    private TextField ChangeScoreSaveField;

    @FXML
    private TextArea ChangeScoreTextArea;

    @FXML
    private HBox FeedbackHbox;

    @FXML
    private TextArea FeedbackTextArea;

    @FXML
    private Text GradeText;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private Label StudentIDLabel;

    @FXML
    private Label ChangeScoreErrLbl;

    @FXML
    private Label StudentNameLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label TitleLabel;
    @FXML
    private TextFlow GradeTF;


    private Person client;


    @FXML
    private BorderPane AnswerTitleBord;

    @FXML
    private BorderPane TableTitleBord1;

    @FXML
    private BorderPane TableTitleBord2;

    //@FXML
    // private BorderPane TableTitleBord3;

    @FXML
    private BorderPane TableTitleBord4;


    int hboxWidth = 650;
    double rowWidth = 700;

    int studentScore;
    private StudentExam solvedExam;

    void SetStudentScore(int newScore)
    {
        studentScore = newScore;
        GradeText.setText(Integer.toString(newScore));
        if (newScore > 50)
            GradeText.setFill(Color.GREEN);
        else
            GradeText.setFill(Color.RED);
    }





    @FXML
    void BackBtnAct(ActionEvent event) throws IOException {
        if (solvedExam.getStatus() == Enums.submissionStatus.NotTaken)
        {
            SimpleChatClient.setRoot("PreviewTestForm");
        }
        else
        {
            SimpleChatClient.setRoot("TeacherExamGrade");
        }
        String subjectStr = solvedExam.getClassExam().getExamForm().getSubject().getName();
        String courseStr = solvedExam.getClassExam().getExamForm().getCourse().getName();
        String ExamExamID = solvedExam.getClassExam().getExamForm().getExamFormID();
        int ExamExamSqlID = solvedExam.getClassExam().getExamForm().getID();
        EventBus.getDefault().post(new ClassExamGradeEvent(subjectStr, courseStr, ExamExamID, ExamExamSqlID));
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void ApproveBtnAct(ActionEvent event) throws IOException {
        solvedExam.setGrade(studentScore);
        solvedExam.setStatus(Enums.submissionStatus.Approved);
        // send to server to set student Exam
        Message studentExamMessage = new Message(0, "Change Student Exam");
        studentExamMessage.setData(solvedExam);
        SimpleClient.getClient().sendToServer(studentExamMessage);
    }

    @FXML
    void ApproveBtnHover(MouseEvent event)
    {
        ApproveBtn.setStyle("-fx-background-color: #74A5FD; -fx-text-fill: white;");
    }

    @FXML
    void ApproveBtnHoverOut(MouseEvent event)
    {
        ApproveBtn.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");
    }

    @FXML
    void ApproveBtnPressed(MouseEvent event)
    {
        ApproveBtn.setStyle("-fx-background-color: #4475CD; -fx-text-fill: white;");
    }

    @FXML
    void ApproveBtnReleased(MouseEvent event)
    {
        ApproveBtn.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");
    }


    @FXML
    void DisapproveBtnAct(ActionEvent event) throws IOException {

        solvedExam.setGrade(-1);
        solvedExam.setStatus(Enums.submissionStatus.Disapproved);
        // send to server to set student Exam
        Message studentExamMessage = new Message(0, "Change Student Exam");
        studentExamMessage.setData(solvedExam);
        SimpleClient.getClient().sendToServer(studentExamMessage);
    }

    @FXML
    void DisapproveBtnHover(MouseEvent event) {
        DisapproveBtn.setStyle("-fx-background-color:  #ED8F8F; -fx-text-fill: white;");
    }

    @FXML
    void DisapproveBtnHoverOut(MouseEvent event) {
        DisapproveBtn.setStyle("-fx-background-color:  #DC6F6F; -fx-text-fill: white;");
    }

    @FXML
    void DispproveBtnPressed(MouseEvent event) {
        DisapproveBtn.setStyle("-fx-background-color: #CB5E5E; -fx-text-fill: white;");
    }

    @FXML
    void DispproveBtnReleased(MouseEvent event) {
        DisapproveBtn.setStyle("-fx-background-color:  #DC6F6F; -fx-text-fill: white;");
    }


    @FXML
    void ChangeScoreAct(ActionEvent event)
    {
        // pop the ChangeScoreHbox and disable FeedbackHbox if needed
        AnswersVBOX.getChildren().remove(ChangeScoreHbox);
        FeedbackHbox.setVisible(false);
        FeedbackHbox.setPrefHeight(0);
        ChangeScoreHbox.setVisible(true);
        ChangeScoreHbox.setPrefHeight(100);
        ChangeScoreErrLbl.setVisible(false);
        AnswersVBOX.getChildren().add(AnswersVBOX.getChildren().size()-1, ChangeScoreHbox);

    }

    @FXML
    void ChangeScoreSaveAct(ActionEvent event)
    {
        try
        {
            // new score check
            int newScore = Integer.parseInt(ChangeScoreSaveField.getText());
            if (newScore > 100 || newScore < 0)
                throw new Exception("out of range");
            ChangeScoreErrLbl.setText("");
            ChangeScoreErrLbl.setPrefHeight(Control.USE_COMPUTED_SIZE);

            // Text area check
            String textAreaStr = ChangeScoreTextArea.getText();
            if (textAreaStr.isBlank())
                throw new Exception("Blank Text Area");
            ChangeScoreTextArea.setPromptText("Please write The reason for the score change");

            // set new score and note
            SetStudentScore(newScore);
            solvedExam.setScoreChangeReason(textAreaStr);

            // close pop up HBOX
            ChangeScoreErrLbl.setVisible(false);
            ChangeScoreHbox.setVisible(false);
            ChangeScoreHbox.setPrefHeight(0);
        }
        catch (Exception e)
        {
            if (e.getMessage().startsWith("out of range"))
            {
                ChangeScoreErrLbl.setText("Insert number between 0-100");
                ChangeScoreErrLbl.setPrefHeight(Control.USE_COMPUTED_SIZE);
                ChangeScoreErrLbl.setVisible(true);
            }
            else if (e.getMessage().startsWith("Blank Text Area"))
            {
                ChangeScoreTextArea.setPromptText("You must enter the reason for score changing");
            }
            else
            {
                ChangeScoreErrLbl.setText("Wrong Input");
                ChangeScoreErrLbl.setPrefHeight(Control.USE_COMPUTED_SIZE);
                ChangeScoreErrLbl.setVisible(true);
            }
        }
    }

    @FXML
    void ExamFeedbackAct(ActionEvent event)
    {
        // pop the FeedbackHbox and disable ChangeScoreHbox if needed
        AnswersVBOX.getChildren().remove(FeedbackHbox);
        ChangeScoreHbox.setVisible(false);
        ChangeScoreHbox.setPrefHeight(0);
        FeedbackHbox.setVisible(true);
        FeedbackHbox.setPrefHeight(100);
        AnswersVBOX.getChildren().add(AnswersVBOX.getChildren().size()-1, FeedbackHbox);
    }


    @FXML
    void FeedbackSaveAct(ActionEvent event)
    {
        solvedExam.setTeacherNote(FeedbackTextArea.getText());
        // close pop up HBOX
        FeedbackHbox.setVisible(false);
        FeedbackHbox.setPrefHeight(0);
    }

    @Subscribe
    public void ExamApproved(RefreshPerson event) throws InterruptedException {
        Platform.runLater(() -> {
        String msg = event.getMessage();
        if (msg.startsWith("Success"))
        {
            try
            {
                SimpleClient.getClient().setUser(event.getPerson());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES);
                alert.setTitle("Student grading saved");
                alert.setHeaderText("CONFIRMATION: Saved");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)
                {
                    SimpleChatClient.setRoot("TeacherExamGrade");
                    String subjectStr = solvedExam.getClassExam().getExamForm().getSubject().getName();
                    String courseStr = solvedExam.getClassExam().getExamForm().getCourse().getName();
                    String ExamExamID = solvedExam.getClassExam().getExamForm().getExamFormID();
                    int ExamExamSqlID = solvedExam.getClassExam().getExamForm().getID();
                    EventBus.getDefault().post(new ClassExamGradeEvent(subjectStr, courseStr, ExamExamID, ExamExamSqlID));
                    EventBus.getDefault().unregister(this);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else if (msg.startsWith("Failure"))
        {
            solvedExam.setStatus(Enums.submissionStatus.ToEvaluate);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Student grading failed to save");
            alert.setHeaderText("Error: Saving Failed");
            alert.show();
        }
        });
    }


    private TextFlow GenerateText(String Bold, String content)
    {
        Text text1 = new Text(Bold);
        text1.setStyle("-fx-font-weight: bold;");
        Text text2 = new Text(content);
        TextFlow headerFlow = new TextFlow();
        headerFlow.getChildren().addAll(text1, text2);
        headerFlow.setMaxWidth(hboxWidth);
        headerFlow.setPrefHeight(Control.USE_COMPUTED_SIZE);
        headerFlow.setTextAlignment(TextAlignment.LEFT);
        headerFlow.setPadding(new Insets(20, 20, 20 ,20));
        return headerFlow;
    }


    @Subscribe
    public void getStarterData(StudentExamEvent event) throws IOException {
        solvedExam = event.getStudentExam();
        Platform.runLater(() -> {
            try {
                GetQuestions(AnswersVBOX);

                if(solvedExam.getTeacherNote() != null)
                {
                    FeedbackTextArea.setText(solvedExam.getTeacherNote());
                }
                if(solvedExam.getScoreChangeReason() != null)
                    ChangeScoreTextArea.setText(solvedExam.getScoreChangeReason());

                // set the scroll to top after estimated time of scene rendering
                PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
                pause.setOnFinished(timedEvent -> ScrollPane.setVvalue(0));
                pause.play();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    void initialize() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        // set the unnecessary nodes to invisible
        FeedbackHbox.setVisible(false);
        FeedbackHbox.setPrefHeight(0);
        ChangeScoreHbox.setVisible(false);
        ChangeScoreHbox.setPrefHeight(0);
        client = SimpleClient.getUser();
        if (client instanceof Student)
        {
            ShowStatisticsController.SetVisibleAllNodes(ButtonsHbox,false);
            BackBtn.setDisable(true);
            BackBtn.setVisible(false);
        }


    }

    VBox GetQuestions(VBox AnswersVBOX_t) throws IOException, InterruptedException {
        while (solvedExam == null)
        {
            System.out.println("solvedExam is null");
            TimeUnit.SECONDS.sleep(1);
        }


        // Initialize Widths

        AnswersVBOX_t.setPrefWidth(rowWidth);
        AnswerTitleBord.setPrefWidth(rowWidth);
        TableTitleBord1.setPrefWidth(1*rowWidth/10);
        TableTitleBord2.setPrefWidth(8*rowWidth/10);
        //TableTitleBord3.setPrefWidth(1*rowWidth/10);
        TableTitleBord4.setPrefWidth(1*rowWidth/10);


        int IndexPlace = 0;
        String headerStr = solvedExam.getClassExam().getExamForm().getHeaderText();
        if(headerStr != null)
        {
            // TODO: change "header" string
            AnswersVBOX_t.getChildren().add( IndexPlace++, GenerateText("Header: ", headerStr));
        }

        if(client instanceof Teacher || client instanceof Principal)
        {
            String teachersNote = solvedExam.getClassExam().getExamForm().getExamNotesForTeacher();
            if(teachersNote != null)
            {

                AnswersVBOX_t.getChildren().add( IndexPlace++,
                        GenerateText("Teacher Notes: ", teachersNote));
            }
        }



        String studentsNotes = solvedExam.getClassExam().getExamForm().getExamNotesForStudent();
        if(studentsNotes != null)
        {
            AnswersVBOX_t.getChildren().add( IndexPlace++,
                    GenerateText("Student Notes: ", studentsNotes));
        }
        IndexPlace += 2;
        int QuestionIndexPlace = IndexPlace;

        String footerStr = solvedExam.getClassExam().getExamForm().getFooterText();
        if(footerStr != null)
        {
            // TODO: change "footer" string
            AnswersVBOX_t.getChildren().add( IndexPlace++,
                    GenerateText("Footer: ", footerStr));
        }

        if(client instanceof Student)
        {
            String feedback = solvedExam.getTeacherNote();
            if(feedback != null)
            {
                AnswersVBOX_t.getChildren().add( IndexPlace++,
                        GenerateText("Feedback: ", feedback));
            }

            String scoreChangeREASON = solvedExam.getScoreChangeReason();
            if(scoreChangeREASON != null)
            {
                AnswersVBOX_t.getChildren().add( IndexPlace++,
                        GenerateText("Score Change Reason: ", scoreChangeREASON));
            }
        }



        List<Question> questions = solvedExam.getClassExam().getExamForm().getQuestionList();
        int studentScore = 0;

        List<String> studentAnswers = solvedExam.getStudentAnswers();
        for (int questionNumber = 0; studentAnswers != null && questionNumber < questions.size(); questionNumber++)
        {


            Question question = questions.get(questionNumber);
            String correctAnswer = question.getCorrectAnswer();
            int correctAnswerInt = question.getAnswers().indexOf(correctAnswer) + 1;
            String studentAnswerStr = "";
            if (questionNumber < studentAnswers.size())
                studentAnswerStr = studentAnswers.get(questionNumber);

            int studentAnswerInt =  question.getAnswers().indexOf(studentAnswerStr) + 1;
            int questionScoreInt = solvedExam.getClassExam().getExamForm().getQuestionsScores().get(questionNumber);
            HBox qustionHbox = new HBox();

            qustionHbox.setPrefWidth(rowWidth);

            // Set question number
            BorderPane bord1 = new BorderPane();
            bord1.setPrefHeight(68);
            bord1.setPrefWidth(rowWidth / 10);
            //bord1.setStyle("-fx-background-color: #F0F8FF; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
            //        "-fx-border-width: 2 2 2 4");
            bord1.setStyle("-fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
                            "-fx-border-width: 2 2 2 4");
            Label questionNumberLbl = new Label(Integer.toString(questionNumber+1));

            bord1.setCenter(questionNumberLbl);

            // Set for answers options
            BorderPane bord2 = new BorderPane();
            VBox answersVbox = new VBox();

            // insert the question text

            Text porblemtext = new Text("Question " + (questionNumber + 1) + ": ");
            porblemtext.setStyle("-fx-font-weight: bold");

            Text contenttext = new Text(question.getQuestionData());
            contenttext.setStyle("-fx-font-weight: regular");

            TextFlow questionText = new TextFlow();
            //questionText.setPrefWidth(250);
            questionText.getChildren().addAll(porblemtext, contenttext);
            questionText.setStyle("-fx-background-color: #7CB9E8; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
                    "-fx-border-width: 2");
            answersVbox.getChildren().add(questionText);


            int answerNum = question.getAnswers().size();
            // insert the 4 answer
            for (int j = 1; j < answerNum + 1; j++)
            {
                HBox answerHbox = new HBox();
                answerHbox.setStyle("-fx-border-color: black;");


                BorderPane bordLoop1 = new BorderPane();
                if (j % 2 == 0)
                    bordLoop1.setStyle("-fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                    //bordLoop1.setStyle("-fx-background-color: #B9D9EB; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                else
                    bordLoop1.setStyle("-fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                    //bordLoop1.setStyle("-fx-background-color: #E6E6FA; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");

                Label answerNumber = new Label(Integer.toString(j));
                answerNumber.setPadding(new Insets(0, 10, 0 ,10));
                bordLoop1.setCenter(answerNumber);

                BorderPane bordLoop2 = new BorderPane();
                HBox.setHgrow(bordLoop2, javafx.scene.layout.Priority.ALWAYS);
                Label answerText = new Label(question.getAnswers().get(j-1));
                answerText.setWrapText(true);
                answerText.setPadding(new Insets(0, 10, 0 ,10));
                // correct answer style
                if(correctAnswerInt == j)
                {
                    bordLoop2.setStyle("-fx-background-color: #65A873; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                    answerText.setStyle("-fx-text-fill: white;");
                }
                else if(studentAnswerInt == j)
                {
                    bordLoop2.setStyle("-fx-background-color: #DC6F6F; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                    answerText.setStyle("-fx-text-fill: white;");
                }
                else
                {
                    bordLoop2.setStyle("-fx-padding: 10px; -fx-border-color: black;");
                    //    answerText.setStyle("-fx-text-fill: white;");
                }

                //// wrong answer style
                //else
                //{
                //    bordLoop2.setStyle("-fx-background-color: #DC6F6F; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;");
                //    answerText.setStyle("-fx-text-fill: white;");
                //}
                bordLoop2.setCenter(answerText);

                answerHbox.getChildren().add(bordLoop1);
                answerHbox.getChildren().add(bordLoop2);
                answerHbox.setAlignment(Pos.CENTER_LEFT);
                answerHbox.setPrefHeight(Control.USE_COMPUTED_SIZE);

                answersVbox.getChildren().add(answerHbox);

            }

            answersVbox.setPrefHeight(Control.USE_COMPUTED_SIZE);
            answersVbox.setAlignment(Pos.CENTER);

            bord2.setCenter(answersVbox);
            bord2.setPrefHeight(Control.USE_COMPUTED_SIZE);
            bord2.setPrefWidth(rowWidth*8 / 10);
            //bord2.setPrefWidth(400);
            bord2.setStyle("-fx-border-color: black;");

            //// Set student answer
            //BorderPane bord3 = new BorderPane();
            //Label studentAnswer = new Label(Integer.toString(question.getAnswers().indexOf(studentAnswerStr)+1));
            //studentAnswer.setAlignment(Pos.CENTER);
            //bord3.setCenter(studentAnswer);
            //bord3.setPrefHeight(Control.USE_COMPUTED_SIZE);
            //bord3.setPrefWidth(rowWidth / 10);
            //
            if (studentAnswerInt == correctAnswerInt)
            {
                studentScore += questionScoreInt;
                //bord3.setStyle("-fx-background-color: #65A873; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
                //        "-fx-border-width: 2");
            }
            //else
            //    bord3.setStyle("-fx-background-color: #DC6F6F; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
            //            "-fx-border-width: 2");

            BorderPane bord4 = new BorderPane();
            Label questionScore = new Label(Integer.toString(questionScoreInt));
            bord4.setPrefWidth(rowWidth*1 / 10);
            bord4.setPrefHeight(Control.USE_COMPUTED_SIZE);
            //bord4.setStyle("-fx-background-color: #F0F8FF; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
            //        "-fx-border-width: 2 4 2 2");
            bord4.setStyle("-fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black;" +
                    "-fx-border-width: 2 4 2 2");
            questionScore.setAlignment(Pos.CENTER);
            bord4.setCenter(questionScore);
            qustionHbox.getChildren().addAll(bord1, bord2, bord4);
            //qustionHbox.getChildren().addAll(bord1, bord2, bord3, bord4);
            AnswersVBOX_t.getChildren().add(QuestionIndexPlace++, qustionHbox);
        }
        if (solvedExam.getStatus() == Enums.submissionStatus.ToEvaluate)
            SetStudentScore(studentScore);
        else
            SetStudentScore(solvedExam.getGrade());

        if (solvedExam.getStatus() != Enums.submissionStatus.NotTaken)
        {
            StudentIDLabel.setText(solvedExam.getStudent().getPersonID());
            StudentIDLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);
            StudentNameLabel.setText(solvedExam.getStudent().getFullName());
            StudentNameLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);
        }
        else
        {
            StudentIDLabel.setVisible(false);
            StudentNameLabel.setVisible(false);
            IDLabel.setVisible(false);
            NameLabel.setVisible(false);
            TitleLabel.setText("Exam Preview");
            ShowStatisticsController.SetVisibleAllNodes(ButtonsHbox,false);
            ShowStatisticsController.SetVisibleAllNodes(GradeTF,false);
            ShowStatisticsController.DisableAllNodes(ButtonsHbox,true);
            AnswerTitleBord.setVisible(false);

        }
        TitleLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);

        return AnswersVBOX_t;
    }



}
