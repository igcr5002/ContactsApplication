package com.ignat.contacts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ContactData {

    private static ContactData instance = new ContactData();
    private static String fileName = "MyContact.txt";

    private ObservableList<ContactEntry> contacts;

    public static ContactData getInstance() {
        return instance;
    }

    public ObservableList<ContactEntry> getContacts() {
        return contacts;
    }

    public void addContact(ContactEntry contact) {
        contacts.add(contact);
    }

    public void deleteContact(ContactEntry contact) {
        contacts.remove(contact);
    }

    public void loadContacts() throws IOException {
        contacts = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while((input=br.readLine())!=null) {
                String[] inputArray = input.split("\t");

                contacts.add(new ContactEntry(inputArray[0],inputArray[1],inputArray[2],inputArray[3]));
            }
        } finally {
            if(br!= null){
                br.close();
            }
        }
    }

    public void saveContacts() throws IOException {
        Path path = Paths.get(fileName);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {

            Iterator<ContactEntry> iterator = ContactData.getInstance().getContacts().listIterator();
            while (iterator.hasNext()) {
                ContactEntry contact = iterator.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        contact.getNume(),
                        contact.getPrenume(),
                        contact.getTelefon(),
                        contact.getEmail()));
                bw.newLine();
            }

        } finally {
            if(bw!=null) {
                bw.close();
            }
        }
    }
}