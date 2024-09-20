package fr.afpa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactDAO extends DAO<Contact> {


    public Contact create(Contact obj) {

        return null;
    }

    public Contact find(long id) {

        return null;

    }

    public ArrayList<Contact> findAll(){

        try{

            
            ArrayList<Contact> contacts = new ArrayList<>();

            Statement stm = this.con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM contact");

            while(result.next()){
                String id = result.getString("id");
                String first = result.getString("first_name");
                String last = result.getString("last_name");
                String gender = result.getString("gender");
                LocalDate birthdate = result.getDate("birth_date").toLocalDate();
                String pseudo = result.getString("pseudo");
                String privateNumber = result.getString("personal_phone_number");
                String professionalNumber = result.getString("professional_phone_number");
                String mailAdress = result.getString("email");
                String postalAdress = result.getString("address");
                String github = result.getString("git_link");

                Contact contact = new Contact(last, first, gender, birthdate, pseudo, privateNumber, professionalNumber, mailAdress, postalAdress, github, id);
                contacts.add(contact);
            }

            
            stm.close();
            result.close();
            con.close();
            return contacts;
        }
        catch (Exception e){
            System.err.println("Error");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Contact update(Contact obj) {

        return null;
    }

    public boolean delete(Contact obj) {


        return true;
    }


}
