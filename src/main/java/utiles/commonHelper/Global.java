package utiles.commonHelper;


public class Global {
    static Global Instance;
    private String Password;
    private String MobileNumber;
    private String Email;
    private String FullName;

    private Global() {
    }

    public static Global getInstance() {
        if (Instance == null) {
            Instance = new Global();
        }
        return Instance;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword() {
        this.Password = RandomSource.generatePassword();
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail() {
        Email = RandomSource.generateRandomEmail();
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber() {
        MobileNumber = RandomSource.generatePhoneNumber();
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName() {
        FullName = RandomSource.generateFullName();
    }
}
