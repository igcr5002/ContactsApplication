package com.ignat.contacts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContactEntry {

    private StringProperty nume;
    private StringProperty prenume;
    private StringProperty telefon;
    private StringProperty email;

    public ContactEntry(String nume, String prenume, String telefon, String email) {
        this.nume = new SimpleStringProperty(nume);
        this.prenume = new SimpleStringProperty(prenume);
        this.telefon = new SimpleStringProperty(telefon);
        this.email = new SimpleStringProperty(email);
    }

    public String getPrenume() {
        return prenume.get();
    }

    public void setPrenume(String prenume) {
        this.prenume.set(prenume);
    }

    public StringProperty prenumeProperty() {
        return prenume;
    }

    public String getNume() {
        return nume.get();
    }

    public void setNume(String nume) {
        this.nume.set(nume);
    }

    public StringProperty numeProperty() {
        return nume;
    }

    public String getTelefon() {
        return telefon.get();
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }

    public StringProperty telefonProperty() {
        return telefon;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }
}
