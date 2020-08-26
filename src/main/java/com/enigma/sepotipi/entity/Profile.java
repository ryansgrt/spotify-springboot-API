package com.enigma.sepotipi.entity;

import com.enigma.sepotipi.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mst_profile")
public class Profile {

    @Id
    @GeneratedValue(generator = "artist_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "artist_uuid", strategy = "uuid")
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String email;
    private String phone;

//    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private String location;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = "profile")
    private Account account;

    @Transient
    private String accountId;

    public Profile() {
    }

    public Profile(String id, String firstName, String middleName, String lastName, String email, String phone, Date birthDate, String location, String accountId) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.location = location;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) &&
                Objects.equals(firstName, profile.firstName) &&
                Objects.equals(middleName, profile.middleName) &&
                Objects.equals(lastName, profile.lastName) &&
                gender == profile.gender &&
                Objects.equals(email, profile.email) &&
                Objects.equals(phone, profile.phone) &&
                Objects.equals(birthDate, profile.birthDate) &&
                Objects.equals(location, profile.location) &&
                Objects.equals(account, profile.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, gender, email, phone, birthDate, location, account);
    }
}
