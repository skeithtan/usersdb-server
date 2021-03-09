package fr.epita.userdb.datatransfer;

import fr.epita.userdb.entities.UserContact;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserContactDTO implements EntityDataTransferObject<UserContact> {
    @NotNull
    private String name;

    @NotNull
    private Date birthDate;

    @NotNull
    private String sex;

    @NotNull
    private String email;

    public UserContact toEntity() {
        var contact = new UserContact();
        contact.setName(this.getName());
        contact.setBirthDate(this.getBirthDate());
        contact.setSex(this.getSex());
        contact.setEmail(this.getEmail());
        return contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
