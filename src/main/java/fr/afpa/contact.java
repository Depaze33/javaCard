package fr.afpa;

import java.io.Serializable;
import java.time.LocalDate;
// import javax.mail.internet.InternetAddress;



public class contact implements Serializable {
    private String lastName;
    private String firstName;
    private String gender;
    private LocalDate birthDate;
    private String pseudo;
    private String adress;
    private String privateNumber;
    private String professionalNumber;
    private String mailAdress;
    private String postalAdress;
    private String github;
    public contact(String lastName, String firstName, String gender, LocalDate birthDate, String pseudo, String adress,
            String privateNumber, String professionalNumber, String mailAdress, String postalAdress, String github) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.pseudo = pseudo;
        this.adress = adress;
        this.privateNumber = privateNumber;
        this.professionalNumber = professionalNumber;
        this.mailAdress = mailAdress;
        this.postalAdress = postalAdress;
        this.github = github;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getPrivateNumber() {
        return privateNumber;
    }
    public void setPrivateNumber(String privateNumber) {
        this.privateNumber = privateNumber;
    }
    public String getProfessionalNumber() {
        return professionalNumber;
    }
    public void setProfessionalNumber(String professionalNumber) {
        this.professionalNumber = professionalNumber;
    }
    public String getMailAdress() {
        return mailAdress;
    }
    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
        
    }
    public String getPostalAdress() {
        return postalAdress;
    }
    public void setPostalAdress(String postalAdress) {
        this.postalAdress = postalAdress;
    }
    public String getGithub() {
        return github;
    }
    public void setGithub(String github) {
        this.github = github;
    }
    @Override
    public String toString() {
        return "contact [lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + ", birthDate="
                + birthDate + ", pseudo=" + pseudo + ", adress=" + adress + ", privateNumber=" + privateNumber
                + ", professionalNumber=" + professionalNumber + ", mailAdress=" + mailAdress + ", postalAdress="
                + postalAdress + ", github=" + github + "]";
    }
   
    



    
}
