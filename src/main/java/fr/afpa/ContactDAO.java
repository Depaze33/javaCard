package fr.afpa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ContactDAO extends DAO<Contact> {

    public Contact create(Contact obj) {
        try {

            String[] fields = {
            "first_name",
			"last_name",
			"gender",
			"pseudo",
			"email",
			"birth_date",
			"address",
			"personal_phone_number",
			"professional_phone_number",
			"git_link"
            };

            String birthdate = (obj.getBirthDate() != null) ? obj.getBirthDate().toString() : null;

            String[] values = {
            obj.getFirstName(),
            obj.getLastName(),
            obj.getGender(),
            obj.getPseudo(),
            obj.getMailAdress(),
            birthdate,
            obj.getPostalAdress(),
            obj.getPrivateNumber(),
            obj.getProfessionalNumber(),
            obj.getGithub(),
            };

            StringBuilder fieldsQuery = new StringBuilder("(");
            StringBuilder prepareQuery = new StringBuilder("(");

            int fieldsCount = 0;
            ArrayList<String> finalValues = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                if (values[i] != null && !values[i].isEmpty()){
                    if (fieldsQuery.length() > 2) {
                        fieldsQuery.append(", ");
                        prepareQuery.append(", ");
                    }
                    fieldsQuery.append(fields[i]);
                    prepareQuery.append("?");
                    fieldsCount++;
                    finalValues.add(values[i]);
                }
            }


            fieldsQuery.append(")");
            prepareQuery.append(")");

            PreparedStatement stm = this.con.prepareStatement(
                    "INSERT INTO contact " + fieldsQuery.toString() + " VALUES " + prepareQuery.toString() + ";", Statement.RETURN_GENERATED_KEYS);

            for (int i = 1; i <= fieldsCount; i++) {
                stm.setString(i, finalValues.get(i - 1));
            }


            
            stm.execute();
            ResultSet rs = stm.getGeneratedKeys();
            if ( rs.next() ) {
                // Retrieve the auto generated key(s).
                obj.setId(String.valueOf(rs.getInt(1)));

            }
            stm.close();
            rs.close();

            return obj;
        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Contact update(Contact obj) {
        try {

            String[] fields = {
            "first_name",
			"last_name",
			"gender",
			"pseudo",
			"email",
			"birth_date",
			"address",
			"personal_phone_number",
			"professional_phone_number",
			"git_link"
            };

            String birthdate = (obj.getBirthDate() != null) ? obj.getBirthDate().toString() : null;

            String[] values = {
            obj.getFirstName(),
            obj.getLastName(),
            obj.getGender(),
            obj.getPseudo(),
            obj.getMailAdress(),
            birthdate,
            obj.getPostalAdress(),
            obj.getPrivateNumber(),
            obj.getProfessionalNumber(),
            obj.getGithub(),
            };

            StringBuilder fieldsQuery = new StringBuilder("(");
            StringBuilder prepareQuery = new StringBuilder("(");

            int fieldsCount = 0;
            ArrayList<String> finalValues = new ArrayList<>();
            ArrayList<String> finalFields = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                if (values[i] != null && !values[i].isEmpty()){
                    if (fieldsQuery.length() > 2) {
                        fieldsQuery.append(", ");
                        prepareQuery.append(", ");
                    }
                    fieldsQuery.append(fields[i]);
                    prepareQuery.append("?");
                    fieldsCount++;
                    finalValues.add(values[i]);
                    finalFields.add(fields[i]);
                }
            }


            fieldsQuery.append(")");
            prepareQuery.append(")");

            PreparedStatement stm = this.con.prepareStatement(
                    "UPDATE contact SET " + fieldsQuery.toString() + " = " + prepareQuery.toString() + " WHERE id="+obj.getId());

            for (int i = 1; i <= fieldsCount; i++) {
                if ("birth_date".equals(finalFields.get(i-1))){
                    stm.setDate(i, java.sql.Date.valueOf(finalValues.get(i - 1)));
                }
                else{
                    stm.setString(i, String.valueOf(finalValues.get(i - 1)));
                }            
            }

            
            stm.execute();
            
            stm.close();

            return obj;
        } catch (Exception e) {
            System.err.println("Error");
            System.err.println(e.getMessage());
        }
        return null;
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
                LocalDate birthdate = (result.getDate("birth_date") != null ) ?  result.getDate("birth_date").toLocalDate() : null ;
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
                LocalDate birthdate = (result.getDate("birth_date") != null ) ?  result.getDate("birth_date").toLocalDate() : null ;
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
