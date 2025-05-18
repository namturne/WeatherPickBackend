package WeatherPick.weatherpick.domain.user.dto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserResponseDto  {

    public static ResponseEntity<UserResponseDto> success(){
        UserResponseDto result =  new UserResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
