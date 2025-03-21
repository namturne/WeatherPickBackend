package WeatherPick.weatherpick.domain.user.dto;

import WeatherPick.weatherpick.domain.user.entity.UserRoleType;

public class UserResponseDto {
    private String username;
    private String nickname;
    private String email;
    private String role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
