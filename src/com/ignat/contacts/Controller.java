package com.ignat.contacts;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;


public class Controller {

    @FXML
    private TableView<ContactEntry> tableView;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        tableView.getItems().setAll(ContactData.getInstance().getContacts());

        ContextMenu menuOnItem = new ContextMenu();
        MenuItem addContactMenu = new MenuItem("Adauga un nou contact");
        addContactMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleAddContact();
            }
        });

        MenuItem deleteContactMenu = new MenuItem("Stergeti contactul selectat");
        deleteContactMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleDeleteContact();
            }
        });

        MenuItem editContactMenu = new MenuItem("Editati contactul selectat");
        editContactMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleEditContact();
            }
        });

        menuOnItem.getItems().addAll(addContactMenu,editContactMenu,deleteContactMenu);

        tableView.setContextMenu(menuOnItem);



    }

    @FXML
    public void handleAddContact() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newContactWindow.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NewContactController controller = fxmlLoader.getController();
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(controller.inputsFullBinding().not());
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            ContactEntry newContact = controller.processResults();
            tableView.getItems().setAll(ContactData.getInstance().getContacts());
        }
    }

    @FXML
    public void handleEditContact() {
        ContactEntry editedContact = tableView.getSelectionModel().getSelectedItem();

        if(editedContact!=null) {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainBorderPane.getScene().getWindow());
            dialog.setTitle("Edit contact: " + editedContact.getNume() + " " + editedContact.getPrenume());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("newContactWindow.fxml"));

            try {

                dialog.getDialogPane().setContent(fxmlLoader.load());

            } catch (IOException e) {
                System.out.println("Could not load the dialog");
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            NewContactController controller = fxmlLoader.getController();
            dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(controller.inputsFullBinding().not());
            controller.showEditContactDetails(editedContact);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.editContact(editedContact);

                tableView.getItems().setAll(ContactData.getInstance().getContacts());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATENTIE!!!");
            alert.setHeaderText("Nu este selectat niciun contact!");
            alert.setContentText("Selectati contactul pe care doriti sa il editati.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleDeleteContact() {
        ContactEntry deletedContact = tableView.getSelectionModel().getSelectedItem();

        if(deletedContact!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare stergere contact");
            alert.setHeaderText("Doriti sa stergeti contactul " + deletedContact.getNume() + " " + deletedContact.getPrenume() +"?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                ContactData.getInstance().getContacts().remove(deletedContact);
                tableView.getItems().setAll(ContactData.getInstance().getContacts());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATENTIE!!!");
            alert.setHeaderText("Nu este selectat niciun contact!");
            alert.setContentText("Selectati contactul pe care doriti sa il stergeti.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

}
