package fr.epita.userdb.datatransfer;

import fr.epita.userdb.entities.User;

public class UserDTO {
    private long id;
    private String username;
    private String[] roles;

    public static UserDTO fromEntity(User user) {
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
