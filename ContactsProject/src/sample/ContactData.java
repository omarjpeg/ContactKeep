package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;


public class ContactData {

    private static final String CONTACTS_FILE = "contacts.xml";

    private static final String CONTACT = "contact";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String NOTES = "notes";
    private static ContactData instance = new ContactData();
    private static final String FAVOURITE = "favourite";
    private ObservableList<Contact> contacts;

    public ContactData() {
       contacts = FXCollections.observableArrayList();
    }

    public ObservableList<Contact> getlist(){
        return  contacts;
    }
    public void addContact( Contact a){
        contacts.add(a);
    }
    public void deleteContact( Contact a){
        contacts.remove(a);
    }
    public static ContactData getInstance(){
        return instance;
    }

    public void loadContacts() {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            InputStream in = new FileInputStream(CONTACTS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Contact contact = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(CONTACT)) {
                        contact = new Contact();
                        continue;
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FIRST_NAME)) {
                            event = eventReader.nextEvent();
                            contact.setFirstName(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(LAST_NAME)) {
                        event = eventReader.nextEvent();
                        contact.setLastName(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(PHONE_NUMBER)) {
                        event = eventReader.nextEvent();
                        contact.setPhoneNumber(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(NOTES)) {
                        event = eventReader.nextEvent();
                        contact.setNotes(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(FAVOURITE)) {
                        event = eventReader.nextEvent();
                        if(event.asCharacters().getData().equals("true")){
                        contact.setIsfavourite(true);}
                        else{
                            contact.setIsfavourite(false);}

                    }
                        continue;
                    }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(CONTACT)) {
                        contacts.add(contact);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void saveContacts() {

        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(new FileOutputStream(CONTACTS_FILE));
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement contactsStartElement = eventFactory.createStartElement("",
                    "", "contacts");
            eventWriter.add(contactsStartElement);
            eventWriter.add(end);

            for (Contact contact: contacts) {
                saveContact(eventWriter, eventFactory, contact);
            }

            eventWriter.add(eventFactory.createEndElement("", "", "contacts"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Problem with Contacts file: " + e.getMessage());
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            System.out.println("Problem writing contact: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveContact(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Contact contact)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        StartElement configStartElement = eventFactory.createStartElement("",
                "", CONTACT);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        createNode(eventWriter, FIRST_NAME, contact.getFirstName());
        createNode(eventWriter, LAST_NAME, contact.getLastName());
        createNode(eventWriter, PHONE_NUMBER, contact.getPhoneNumber());
        createNode(eventWriter, NOTES, contact.getNotes());
        createNode(eventWriter, FAVOURITE, contact.getIsfavourite().toString());
        eventWriter.add(eventFactory.createEndElement("", "", CONTACT));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

}