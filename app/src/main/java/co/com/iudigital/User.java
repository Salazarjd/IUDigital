package co.com.iudigital;

import java.io.Serializable;

public class User implements Serializable{

    private String document;
    private String name;
    private String lastName;
    private String email;
    private String password;

    public User() {
    }

    public User(String document, String name, String lastName, String email, String password) {
        this.document = document;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
