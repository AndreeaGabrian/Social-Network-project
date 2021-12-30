package com.example.networkgui;

import com.company.domain.Message;
import com.company.domain.User;
import com.company.dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DialogViewMessageController extends SuperController{
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label replayedToLabel;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextArea textAreaAnswer;
    @FXML
    private Button replayButton;
    @FXML
    private Button replayAllButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;

    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
        setFields();
    }

    @FXML
    private void initialize(){
    }

    private void setFields(){
        fromLabel.setText(new UserDTO(message.getFrom().getEmail(), message.getFrom().getFirstName(), message.getFrom().getLastName()).toString());
        List<String> emails = message.getTo().stream().map(User::getEmail).collect(Collectors.toList());
        String emailsString = "";
        for (String email: emails) {
            emailsString = emailsString + " " + email;
        }

        toLabel.setText(emailsString);

        messageTextArea.setText(message.getMessage());
        Message repl = message.getReplay();
        Text replText;
        if(repl != null){
            replText = new Text("["+repl.getFrom().getEmail()+"]" + " " + repl.getMessage());
        }else{
            replText = new Text("no replay");
        }
        replayedToLabel.setText(replText.getText());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd MMM yyyy HH:mm");
        dateLabel.setText(message.getDateTime().format(dtf));

    }

    @FXML
    public void replay(ActionEvent actionEvent) {
        String text = textAreaAnswer.getText();
        List<String> emails = new ArrayList<>();
        emails.add(message.getFrom().getEmail());
        if(!Objects.equals(text, "")){
            controller.sendMessageToMultipleUsers(emails, text, message.getId());
        }else{
            MessageAlert.showErrorMessage(null, "Cannot send an empty message!");
        }

    }

    @FXML
    public void replayAll(ActionEvent actionEvent) {
        String text = textAreaAnswer.getText();
        List<String> emails = new ArrayList<>();
        emails.add(message.getFrom().getEmail());
        emails.addAll(message.getTo().stream().map(User::getEmail).collect(Collectors.toList()));
        emails.remove(loginManager.getLogged().getEmail());

        if(!Objects.equals(text, "")){
            controller.sendMessageToMultipleUsers(emails, text, message.getId());
        }else{
            MessageAlert.showErrorMessage(null, "Cannot send an empty message!");
        }
    }

    @FXML
    public void cancelDialog(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void handleCancelButton(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
