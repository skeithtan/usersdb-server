package fr.epita.userdb.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String secret;

    private String[] roles;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UserContact contact;

    public enum Role {
        CREATE_MOVIES,
        MODIFY_MOVIES,
        VIEW_MOVIES,
        RATE_MOVIES;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = Arrays.stream(roles).map(Enum::name).toArray(String[]::new);
    }

    public UserContact getContact() {
        return contact;
    }

    public void setContact(UserContact contact) {
        this.contact = contact;
    }
}
