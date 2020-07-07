package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddDialogController {
    @FXML
    private TextArea firstnamefield;
    @FXML
    private TextArea lastnamefield;
    @FXML
    private TextArea phonenofield;
    @FXML
    private TextArea notesfield;

    public  void process(boolean fave){
        Contact current = new Contact(new SimpleStringProperty(firstnamefield.getText()),new SimpleStringProperty(lastnamefield.getText()),new SimpleStringProperty(phonenofield.getText()),new SimpleStringProperty(notesfield.getText()));
        current.setIsfavourite(fave);
        ContactData.getInstance().addContact(current);
    }

    public TextArea getFirstnamefield() {
        return firstnamefield;
    }

    public TextArea getLastnamefield() {
        return lastnamefield;
    }

    public TextArea getPhonenofield() {
        return phonenofield;
    }

    public TextArea getNotesfield() {
        return notesfield;
    }
}
