package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableView<Contact> table;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> phoneNoColumn;
    @FXML
    private TableColumn<Contact, String> notesColumn;
    @FXML
    private ToggleButton togglebutton;

    private FilteredList<Contact> favouriteList;

    public void initialize() {

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        table.setItems(ContactData.getInstance().getlist());
        TableColumn<Contact, CheckBox> column = (TableColumn<Contact, CheckBox>) table.getColumns().get(0);
        column.setCellValueFactory(new FavouriteCellFactory());

    }


    public void addButtonPressed() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddDialog.fxml"));
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setTitle("Add a new contact");
        dialog.setHeaderText("Add your contact details and press OK");
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            AddDialogController controller = loader.getController();
            controller.process(false);
        }
    }

    public void deletebuttonpressed() {
        Contact contactselected;
        contactselected = table.getSelectionModel().getSelectedItem();
        if (contactselected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You must select a contact first!");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setWidth(500);
        confirm.setHeaderText("Delete selected item" + "?");
        confirm.setContentText("Item with first name \"" + contactselected.getFirstName() + "\" will be permanently lost");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            ContactData.getInstance().deleteContact(contactselected);
        }

    }

    public void editbuttonpressed() throws IOException {
        Contact contactselected;
        contactselected = table.getSelectionModel().getSelectedItem();
        if (contactselected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You must select a contact first!");
            alert.showAndWait();
            return;
        }


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddDialog.fxml"));
        dialog.getDialogPane().setContent(loader.load());
        dialog.setTitle("Edit a contact");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AddDialogController controller = loader.getController();
        controller.getFirstnamefield().setText(contactselected.getFirstName());
        controller.getLastnamefield().setText(contactselected.getLastName());
        controller.getNotesfield().setText(contactselected.getNotes());
        controller.getPhonenofield().setText(contactselected.getPhoneNumber());
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            boolean isfave = false;
            if (contactselected.getIsfavourite()) {
                isfave = true;
            }
            ContactData.getInstance().deleteContact(contactselected);
            controller.process(isfave);
        }
    }

    public void togglebuttonpressed() {
        if (togglebutton.isSelected()) {
            favouriteList = new FilteredList<>(ContactData.getInstance().getlist(), new Predicate<Contact>() {
                @Override
                public boolean test(Contact contact) {
                    return contact.getIsfavourite();
                }
            });
            table.setItems(favouriteList);
        } else {
            table.setItems(ContactData.getInstance().getlist());
        }
    }

    public class FavouriteCellFactory implements Callback<TableColumn.CellDataFeatures<Contact, CheckBox>, ObservableValue<CheckBox>> {
        @Override
        public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Contact, CheckBox> param) {
            Contact person = param.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.setAlignment(Pos.CENTER);
            checkBox.selectedProperty().setValue(person.getIsfavourite());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
                person.setIsfavourite(new_val);
            });
            return new SimpleObjectProperty<>(checkBox);
        }
    }


}

