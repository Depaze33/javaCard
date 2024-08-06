package fr.afpa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ContactVCardSerializer implements Serializer<Contact> {

    @Override
    public void saveList(String filePath, ArrayList<Contact> contacts) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (Contact contact : contacts) {
                String vCard = convertToVCard(contact);
                fileWriter.write(vCard + "\n");
            }
        }
    }

    @Override
    public void save(String filePath, Contact contact) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            String vCard = convertToVCard(contact);
            fileWriter.write(vCard);
        }
    }

    private String convertToVCard(Contact contact) {
        StringBuilder vCard = new StringBuilder();
        vCard.append("BEGIN:VCARD\n");
        vCard.append("VERSION:4.0\n");

        vCard.append("N:").append(contact.getLastName()).append(contact.getFirstName()).append(";;").append(contact.getGender()).append(";").append("\n");
        vCard.append("X-ANDROID-CUSTOM:").append(contact.getPseudo()).append("\n");
        vCard.append("TEL;TYPE=WORK,VOICE:").append(contact.getProfessionalNumber()).append("\n");
        vCard.append("TEL;TYPE=HOME,VOICE:").append(contact.getPrivateNumber()).append("\n");
        vCard.append("EMAIL;TYPE=PREF,INTERNET:").append(contact.getMailAdress()).append("\n");
        vCard.append("ADR;TYPE=HOME:;;").append(contact.getPostalAdress()).append("\n");
        vCard.append("GIT:").append(contact.getGithub()).append("\n");
        vCard.append("END:VCARD\n");
        return vCard.toString();
    }
}
