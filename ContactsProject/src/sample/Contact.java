package sample;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private  SimpleStringProperty PhoneNumber;
    private SimpleStringProperty notes;
    private Boolean isfavourite;

    public Contact() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.PhoneNumber = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.isfavourite = false;
    }

    public Contact(SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty phoneNumber, SimpleStringProperty notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.notes = notes;
        this.isfavourite = false;

    }

    public Boolean getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(Boolean isfavourite) {
        this.isfavourite = isfavourite;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return PhoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
