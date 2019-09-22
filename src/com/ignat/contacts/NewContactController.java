package com.ignat.contacts;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;

public class NewContactController {

    @FXML
    public TextField numeTextField;

    @FXML
    public TextField prenumeTextField;

    @FXML
    public TextField numarTelefonTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public DialogPane dialogPaneId;

    private BooleanBinding inputsFull;

    public BooleanBinding inputsFullBinding() {
        return inputsFull ;
    }

    public final boolean getInputsFull() {
        return inputsFull.get();
    }

    public void initialize() {
        inputsFull = new BooleanBinding() {
            {
                bind(numeTextField.textProperty(),
                        prenumeTextField.textProperty(),
                        numarTelefonTextField.textProperty(),
                        emailTextField.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return ! (numeTextField.getText().trim().isEmpty()
                        || prenumeTextField.getText().trim().isEmpty()
                        || numarTelefonTextField.getText().trim().isEmpty()
                        || emailTextField.getText().trim().isEmpty());
            }
        };
    }


    public ContactEntry processResults() {

        String nume = numeTextField.getText().trim();
        String prenume = prenumeTextField.getText().trim();
        String phoneNr = numarTelefonTextField.getText().trim();
        String email = emailTextField.getText().trim();

        ContactEntry newContact = new ContactEntry(nume,prenume,phoneNr,email);
        ContactData.getInstance().getContacts().add(newContact);
        return newContact;

    }

    public void showEditContactDetails(ContactEntry editedContact) {
        numeTextField.setText(editedContact.getNume());
        prenumeTextField.setText(editedContact.getPrenume());
        numarTelefonTextField.setText(editedContact.getTelefon());
        emailTextField.setText(editedContact.getEmail());
    }

    public void editContact(ContactEntry editedContact) {
        editedContact.setNume(numeTextField.getText().trim());
        editedContact.setPrenume(prenumeTextField.getText().trim());
        editedContact.setTelefon(numarTelefonTextField.getText().trim());
        editedContact.setEmail(emailTextField.getText().trim());

    }

}
