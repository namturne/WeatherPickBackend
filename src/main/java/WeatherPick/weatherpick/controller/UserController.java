package WeatherPick.weatherpick.controller;

import WeatherPick.weatherpick.domain.user.dto.SignRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserResponseDto;
import WeatherPick.weatherpick.domain.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Collections;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 루트 → /login 페이지로 리디렉트
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal String sessionUsername, Model model) {
        model.addAttribute("username", sessionUsername);
        return "main";
    }

    // 회원가입 페이지
    @GetMapping("/user/join")
    public String joinPage() {
        return "join";
    }

    // 회원가입 처리 (form POST)
    @PostMapping("/user/join")
    public String joinProcess(SignRequestDto dto) {
        userService.createOneUser(dto);
        return "redirect:/login";
    }

    // 회원 수정 화면
    @GetMapping("/user/update/{username}")
    public String updatePage(
            @PathVariable("username") String targetUsername,
            @AuthenticationPrincipal String sessionUsername,
            Model model
    ) {
        // 본인(sessionUsername) 또는 ADMIN만 접근 가능
        if (userService.isAccess(sessionUsername, targetUsername)) {
            UserResponseDto dto = userService.readOneUser(targetUsername);
            model.addAttribute("USER", dto);
            return "update";
        }
        return "redirect:/login";
    }

    // 회원 수정 처리 (form POST)
    @PostMapping("/user/update/{username}")
    public String updateProcess(
            @PathVariable("username") String targetUsername,
            @RequestParam("password") String newPassword,
            @RequestParam("confirm-password") String confirmPassword,
            @RequestParam("nickname") String newNickname,
            @AuthenticationPrincipal String sessionUsername
    ) {
        // 비밀번호 일치 체크는 프론트 단에서 이미 했으므로, 여기서는 바로 서비스 호출
        if (userService.isAccess(sessionUsername, targetUsername)) {
            UserRequestDto dto = new UserRequestDto();
            dto.setPassword(newPassword);
            dto.setNickname(newNickname);
            userService.updateOneUser(dto, targetUsername);
        }
        return "redirect:/user/update/" + targetUsername;
    }

    // 회원 삭제 처리
    @PostMapping("/user/delete/{username}")
    public String deleteProcess(
            @PathVariable("username") String targetUsername,
            @AuthenticationPrincipal String sessionUsername
    ) {
        if (userService.isAccess(sessionUsername, targetUsername)) {
            userService.deleteOneUser(targetUsername);
            return "redirect:/login";
        }
        return "redirect:/user/update/" + targetUsername;
    }

    // 아이디 중복 확인 (AJAX)
    @GetMapping("/user/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userService.existsByUsername(username);
        return Collections.singletonMap("exists", exists);
    }

    // 이메일 중복 확인 (AJAX)
    @GetMapping("/user/check-email")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
        boolean exists = userService.existsByEmail(email);
        return Collections.singletonMap("exists", exists);
    }
}
