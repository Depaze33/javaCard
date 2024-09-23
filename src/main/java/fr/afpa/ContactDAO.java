package fr.afpa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactDAO extends DAO<Contact> {

    public Contact create(Contact obj) {

        return null;
    }

    public boolean insert(String table, String[] fields, String[] values) {
        try {

            StringBuilder fieldsQuery = new StringBuilder("(");
            StringBuilder prepareQuery = new StringBuilder("(");

            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    fieldsQuery.append(", ");
                    prepareQuery.append(", ");
                }
                fieldsQuery.append(fields[i]);
                prepareQuery.append("?");
            }

            fieldsQuery.append(")");
            prepareQuery.append(")");

            PreparedStatement stm = this.con.prepareStatement(
                    "INSERT INTO " + table + " " + fieldsQuery.toString() + " VALUES " + prepareQuery.toString() + ";");

            for (int i = 1; i <= fields.length; i++) {
                stm.setString(i, values[i - 1]);
            }

            stm.execute();

            stm.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
            return false;
        }

    }

    public Contact find(String id) {

        try {

            PreparedStatement stm = this.con.prepareStatement("SELECT * FROM contact WHERE id=?");
            stm.setInt(1, Integer.valueOf(id));

            ResultSet result = stm.executeQuery();

            while (result.next()) {
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

                Contact contact = new Contact(last, first, gender, birthdate, pseudo, privateNumber, professionalNumber,
                        mailAdress, postalAdress, github, id);

                stm.close();
                result.close();
                System.out.println(contact);
                return contact;
            }
        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }

        return null;

    }

    public ArrayList<Contact> findAll() {

        try {

            ArrayList<Contact> contacts = new ArrayList<>();

            Statement stm = this.con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM contact");

            while (result.next()) {
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

                Contact contact = new Contact(last, first, gender, birthdate, pseudo, privateNumber, professionalNumber,
                        mailAdress, postalAdress, github, id);
                contacts.add(contact);
            }

            stm.close();
            result.close();
            return contacts;
        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Contact update(Contact obj) {

        return null;
    }

    public boolean delete(String id) {

        try {

            PreparedStatement stm = this.con.prepareStatement("DELETE FROM contact WHERE id=?");
            stm.setInt(1, Integer.valueOf(id));
            stm.execute();

            stm.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }
        return false;
    }

}
