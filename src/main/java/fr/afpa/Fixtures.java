package fr.afpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Fixtures {

    public void fixtures(){
        generateFixtures();
    }

    public static void generateFixtures(){
        ArrayList<Contact> contacts = new ArrayList<>();
        String[] names = {"Aaron", "Caitlin", "Mazda", "Bianca", "Brunehilde", "Betsie", "Fabricio", "accessories", "accompaniments", "accouterments", "appliances", "appurtenances", "articles", "attachments", "baggage", "belongings", "contraptions", "contrivances", "devices", "equipage", "facilities", "fittings", "gadgets", "habiliments", "impedimenta", "materiel", "outfit", "paraphernalia", "provisions", "rig", "setup", "shebang", "stock", "store", "stuff", "tackle", "things", "tools", "trappings", "traps", "utensils"};
        String[] genders = {"M", "F", "NB"};

        for (Integer i=0; i<10; i++){
            String firstName = names[randInt(names.length-1)];
            String lastName = names[randInt(names.length-1)];
            String gender = genders[randInt(2)];

            StringBuilder phone = new StringBuilder();
            for(Integer j = 0; j<10; j++){

                phone.append(randInt(9));
            }

            StringBuilder zip = new StringBuilder();
            for(Integer k = 0; k<5; k++){
                zip.append(randInt(9));
            }



            contacts.add(new Contact(firstName, lastName, gender, LocalDate.now(),
            names[randInt(names.length-1)], phone.toString(), phone.toString(), firstName+lastName+"@gmail.com",
            randInt(100)+ " rue des "+ names[randInt(names.length-1)]+ " "+ zip +" "+names[randInt(names.length-1)], "https://github.com/"+firstName+lastName));

        }
       

        App.serializerMethode(contacts);
    }

    public static Integer randInt(Integer max){
        return randInt(0, max);
    }

    public static Integer randInt(Integer min, Integer max) {

    return ThreadLocalRandom.current().nextInt(min, max + 1 );
}
}
