package WeatherPick.weatherpick.domain.user.entity;



public enum UserRoleType {

    ADMIN("어드민"),
    USER("유저");

    private final String description;

    UserRoleType(String description){
        this.description = description;
    }
    @Override
    public String toString() {
        return this.description;  // description 반환
    }
}
