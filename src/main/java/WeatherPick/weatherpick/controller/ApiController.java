package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.user.dto.UserLoginDto;
import WeatherPick.weatherpick.domain.user.dto.UserRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserResponseDto;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import WeatherPick.weatherpick.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ApiController {
    private final UserService userService;
    private final UserRepository userRepository;

    public ApiController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<String> loginProcess(@RequestBody UserLoginDto dto) {
        try {
            userService.checkPassword(dto.getUsername(), dto.getPassword());
            return ResponseEntity.ok("로그인 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    // 회원가입 처리
    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody UserRequestDto dto) {
        userService.createOneUser(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

    // 회원정보 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String username) {
        if (userService.isAccess(username)) {
            return ResponseEntity.ok(userService.readOneUser(username));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 회원 수정
    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserRequestDto dto) {
        if (userService.isAccess(username)) {
            userService.updateOneUser(dto, username);
            return ResponseEntity.ok("회원정보 수정 완료");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
    }

    // 회원 삭제
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (userService.isAccess(username)) {
            userService.deleteOneUser(username);
            return ResponseEntity.ok("삭제 완료");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
    }

    // 아이디 중복 확인
    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userRepository.existsByUsername(username);
        return Collections.singletonMap("exists", exists);
    }

    // 이메일 중복 확인
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return Collections.singletonMap("exists", exists);
    }
}
