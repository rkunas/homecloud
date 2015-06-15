package eu.kunas.homeclowd.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by ramazan on 14.04.15.
 */
public class UserDto implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public UserDto() {

    }

    public UserDto(String u, String p ) {
        this.username = u;
        this.password = p;
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
}
