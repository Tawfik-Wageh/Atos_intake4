package utiles.commonHelper;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomSource {

    static public String generatePhoneNumber() {
        String[] prefixes = {"011", "012", "015"};
        String prefix = prefixes[new java.util.Random().nextInt(prefixes.length)];
        int rest = 10000000 + new java.util.Random().nextInt(90000000);
        return prefix + rest;

    }

    public static String generateFullName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    public static String generatePassword() {
        Faker faker = new Faker();
        String password = faker.internet().password();
        return password;
    }

    public static String generateRandomEmail() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(date);
        String[] domains = {"@gmail.com", "@outlook.com"};
        String selectedDomain = domains[(int) (Math.random() * domains.length)];
        return "Atos" + "_" + timeStamp + selectedDomain;
    }
}
