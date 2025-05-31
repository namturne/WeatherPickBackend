package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.domain.user.dto.*;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import WeatherPick.weatherpick.domain.user.service.AuthService;
import WeatherPick.weatherpick.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthService authService;

    // 1) 로그인 → @RequestBody LoginRequestDto → JWT 토큰 반환
    @PostMapping("/login")
    public ResponseEntity<? super LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return authService.Login(dto);
    }

    // 2) 회원가입 → @RequestBody SignRequestDto
    @PostMapping("/join")
    public ResponseEntity<? super SignResponseDto> signUp(@RequestBody @Valid SignRequestDto dto) {
        return authService.signUp(dto);
    }

    // 3) 내 정보 조회 (JWT 토큰에서 username 꺼내 줌)
    @GetMapping("")
    public ResponseEntity<? super UserInfoResponseDto> getInfoUser(
            @AuthenticationPrincipal String username
    ) {
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return userService.getInfoUser(username);
    }

    // 4) 특정 사용자 조회 (본인 또는 ADMIN 권한)
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable String username,
            @AuthenticationPrincipal String sessionUsername
    ) {
        if (userService.isAccess(sessionUsername, username)) {
            return ResponseEntity.ok(userService.readOneUser(username));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 5) 사용자 수정 (PUT) (본인 또는 ADMIN 권한)
    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(
            @PathVariable String username,
            @RequestBody UserRequestDto dto,
            @AuthenticationPrincipal String sessionUsername
    ) {
        if (userService.isAccess(sessionUsername, username)) {
            userService.updateOneUser(dto, username);
            return ResponseEntity.ok("회원정보 수정 완료");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
    }

    // 6) 사용자 삭제 (DELETE) (본인 또는 ADMIN 권한)
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(
            @PathVariable String username,
            @AuthenticationPrincipal String sessionUsername
    ) {
        if (userService.isAccess(sessionUsername, username)) {
            userService.deleteOneUser(username);
            return ResponseEntity.ok("삭제 완료");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
    }

    // 7) 아이디 중복 확인 (GET → 쿼리 파라미터)
    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userRepository.existsByUsername(username);
        return Collections.singletonMap("exists", exists);
    }

    // 8) 이메일 중복 확인
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return Collections.singletonMap("exists", exists);
    }
}
