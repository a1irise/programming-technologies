package ru.kotiki.dto;

public class UserDto {

    private long id;
    private String username;
    private String password;
    private boolean isAdmin;
    private long ownerId;

    protected UserDto() {

    }

    public UserDto(String username, String password, boolean isAdmin, long ownerId) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.ownerId = ownerId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
