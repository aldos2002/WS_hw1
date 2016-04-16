package com.epam.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Almas_Doskozhin
 * on 4/2/2016.
 */
@XmlRootElement(name = "user")
public class User {
    private String firstName;
    private String lastName;
    private String login;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login= login;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return com.google.common.base.Objects.equal(firstName, user.firstName) &&
                com.google.common.base.Objects.equal(lastName, user.lastName) &&
                com.google.common.base.Objects.equal(login, user.login) &&
                com.google.common.base.Objects.equal(email, user.email);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(firstName, lastName, login, email);
    }
}
