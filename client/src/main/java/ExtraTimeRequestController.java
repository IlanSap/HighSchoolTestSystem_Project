import Entities.Exam;
import Entities.ExtraTime;
import Entities.Message;
import Entities.Principle;
import Events.PrinciplesMessageEvent;
import Events.RunExamEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//** A controller to fill details about the Request to Extra Time**//
public class ExtraTimeRequestController {
    private Exam exam;
    private List<Principle>principles;
    private List<Principle>selectedPrinciples;
    @FXML
    private VBox CBvBox;
    //private VBox checkBoxContainer;
    @FXML
    private TextArea TeacherNoteTF;
    @FXML
    private Button sendBT;

    //**get the principles list from the server**//
    @Subscribe
    public void updatePrinciples(PrinciplesMessageEvent event){
        principles= event.getPrinciples();
    }

    @Subscribe
    public void updateExam(RunExamEvent event)
    {
        exam=event.getExam();
    }
    @FXML
    public void initialize() {
        principles=new ArrayList<>();
        selectedPrinciples=new ArrayList<>();
        /*
        Principle p1=new Principle();
        Principle p2=new Principle();
        Principle p3=new Principle();
        Principle p4=new Principle();
        Principle p5=new Principle();
        p1.setFirstName("liad");
        p1.setLastName("arvatz");
        p2.setFirstName("noa");
        p2.setLastName("arvatz");
        p3.setFirstName("shmuel");
        p3.setLastName("arvatz");
        p4.setFirstName("omer");
        p4.setLastName("artzi");
        p5.setFirstName("ilan");
        p5.setLastName("sapoznikov");
        principles.add(p1);
        principles.add(p2);
        principles.add(p3);
        principles.add(p4);
        principles.add(p5);
        */
        for (int i = 0; i < principles.size(); i++) {
            // Create a CheckBox dynamically
            CheckBox checkBox = new CheckBox(principles.get(i).getFullName());
            CBvBox.getChildren().add(checkBox);
            int finalI = i;
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected())
                    selectedPrinciples.add(principles.get(finalI));
            });
        }
    }

    @FXML
    void sendExtraTimeRequest(ActionEvent event) throws IOException {
        String note=TeacherNoteTF.getText();
        ExtraTime extraTime=new ExtraTime(exam,selectedPrinciples,note);
        Message message = new Message(1,"Extra time request");
        message.setData(extraTime);
        SimpleClient.getClient().sendToServer(message);

    }
}
