package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.user.dto.*;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import WeatherPick.weatherpick.domain.user.service.AuthService;
import WeatherPick.weatherpick.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.management.remote.JMXAuthenticator;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthService authService;


    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<? super LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return authService.Login(dto);
    }

    // 회원가입 처리
    @PostMapping("/join")
    public ResponseEntity<? super SignResponseDto> signUp(@RequestBody @Valid SignRequestDto dto) {
        return authService.signUp(dto);
    }

    @GetMapping("")
    public ResponseEntity<? super UserInfoResponseDto> getInfoUser(@AuthenticationPrincipal String username){
        return userService.getInfoUser(username);
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
