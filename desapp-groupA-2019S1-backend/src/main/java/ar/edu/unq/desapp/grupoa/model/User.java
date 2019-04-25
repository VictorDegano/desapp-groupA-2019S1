package ar.edu.unq.desapp.grupoa.model;

import org.joda.time.DateTime;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private DateTime bornDay;


    public User(String firstName, String lastName, String email, String password, DateTime bornDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bornDay = bornDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getBornDay() {
        return bornDay;
    }

    public void setBornDay(DateTime bornDay) {
        this.bornDay = bornDay;
    }
}
