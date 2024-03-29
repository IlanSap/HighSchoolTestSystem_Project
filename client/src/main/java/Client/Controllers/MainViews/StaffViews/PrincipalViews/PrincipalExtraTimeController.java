package Client.Controllers.MainViews.StaffViews.PrincipalViews;

import Client.Controllers.MainViews.SaveBeforeExit;
import Client.Controllers.MainViews.StaffViews.TeacherViews.TeacherAddQuestionController;
import Client.Events.ExtraTimeRequestsEvent;
import Client.Events.PrincipalApproveEvent;
import Client.Events.PrincipalDecisionEvent;
import Client.Events.PrincipalPressedShowDecision;
import Client.SimpleChatClient;
import Client.SimpleClient;
import Entities.Communication.Message;
import Entities.Communication.ExtraTime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalExtraTimeController extends SaveBeforeExit {
    @FXML
    private Label headerLabel;
    private ExtraTime extraTimeSelected;
    private List<ExtraTime> extraTimeList = new ArrayList<>();
    private ObservableList<ExtraTime> data;
    @FXML
    private TableColumn<ExtraTime, String> InfoColumn;
    @FXML
    private TableColumn<ExtraTime, String> ExtraTimeColumn;
    @FXML
    private ChoiceBox<String> DecisionCoiceBox;
    @FXML
    private TableView<ExtraTime> ExtraTimeList;
    @FXML
    private TableColumn<ExtraTime, String> IDColumn;
    @FXML
    private TextArea PrincipalNote;
    @FXML
    private Button SendButton;
    @FXML
    private TableColumn<ExtraTime, String> SentByColumn;
    @FXML
    private Label DecisionLabel;

    @Subscribe
    public void decisionSaved(PrincipalPressedShowDecision event) throws IOException {
        EventBus.getDefault().unregister(this);
        System.out.println("In decisionSaved");
        JOptionPane.showMessageDialog(null, "Decision sent", "Decision Sent", JOptionPane.INFORMATION_MESSAGE);
            try {
                SimpleChatClient.setRoot("PrincipalMainScreen");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        //EventBus.getDefault().unregister(this);
    }

    /* get the Extra Time request from the data base */
    @Subscribe
    public void update(ExtraTimeRequestsEvent event) {
        extraTimeList = event.getExtraTimeList();
        data.addAll(extraTimeList);
        ExtraTimeList.refresh();
    }

    public void elementStatus(boolean b) {
        DecisionLabel.setVisible(b);
        SendButton.setVisible(b);
        PrincipalNote.setDisable(b);
        DecisionCoiceBox.setVisible(b);
        PrincipalNote.setVisible(b);
    }

    @FXML
    void initialize() throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        elementStatus(false);
        Message message = new Message(1, "Get Extra Time Requests");
        SimpleClient.getClient().sendToServer(message);

        /* intialize the elements */
        data = ExtraTimeList.getItems();

        InfoColumn.setCellValueFactory(new PropertyValueFactory<>("teacherNote"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        SentByColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        ExtraTimeColumn.setCellValueFactory(new PropertyValueFactory<>("delta"));

        String [] str={"Approve", "Reject"};
        DecisionCoiceBox.setItems(FXCollections.observableArrayList(str));

        /* select a request from the table */
        ExtraTimeList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                extraTimeSelected = newSelection;
                DecisionCoiceBox.setVisible(true);
                DecisionLabel.setVisible(true);
            }
        });

        /* check the decision */
        DecisionCoiceBox.setOnAction(event -> {
            if (!DecisionCoiceBox.getSelectionModel().isEmpty() || DecisionCoiceBox.getSelectionModel() != null) {
                    SendButton.setVisible(true);
                    PrincipalNote.setVisible(true);
            }
        });
    }

    @FXML
    void SendDecision(ActionEvent event) throws IOException {

        if(!extraTimeSelected.getDecision().equals("")){
            JOptionPane.showMessageDialog(null, "you already sent your decision", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String decision = DecisionCoiceBox.getValue();
        String principalNote = PrincipalNote.getText();

        extraTimeSelected.setDecision(decision);
        extraTimeSelected.setPrincipalNote(principalNote);
        if (decision.equals("Approve")) {

            Message message = new Message(1, "Extra time approved", extraTimeSelected);
            SimpleClient.getClient().sendToServer(message);

        } else {

            Message message = new Message(1, "Extra time rejected",extraTimeSelected );
            SimpleClient.getClient().sendToServer(message);

        }
    }

    @Override
    public boolean CheckForUnsavedData() {
        if (extraTimeSelected!=null && !DecisionCoiceBox.getValue().isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void SaveData() {
        ActionEvent event = new ActionEvent();
        try {
            SendDecision(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

