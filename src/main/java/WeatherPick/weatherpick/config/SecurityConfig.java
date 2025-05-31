package WeatherPick.weatherpick.config;

import WeatherPick.weatherpick.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import WeatherPick.weatherpick.domain.user.entity.UserRoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 1) BCryptPasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Role 계층 정의 (ADMIN → USER)
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role(UserRoleType.ADMIN.toString()).implies(UserRoleType.USER.toString())
                .build();
    }

    // 3) SecurityFilterChain 정의 걍 gpt돌려버림
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS는 기본 설정 사용
                .cors(Customizer.withDefaults())
                // CSRF 비활성화 (REST API 용)
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 비활성화 (JWT 사용 예정)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않도록 설정 (Stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 실패 시 커스텀 엔트리포인트 사용
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
                // JWT 토큰 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 인가(Authorization) 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입, 중복체크 등은 모두 허용
                        .requestMatchers("/api/user/login", "/api/user/join", "/api/user/check-username", "/api/user/check-email").permitAll()
                        // 날씨 조회 API는 공개
                        .requestMatchers("/weather/**").permitAll()
                        // 장소 등록 페이지와 API는 로그인된 사용자만 허용
                        .requestMatchers("/place", "/place/**").authenticated()
                        // 나머지 /api/**(유저 조회·게시글·댓글 등) 전부 인증 필요
                        .requestMatchers("/api/**").authenticated()
                        // 그 외 모든 경로(HTML 뷰 등)는 모두 허용
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // 4) AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

/**
 * 인증 실패 시(토큰이 없거나 유효하지 않을 때) JSON 형태로 응답을 내려주는 엔트리포인트
 */
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException  // 반드시 org.springframework.security.core.AuthenticationException 사용
    ) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("{\"code\":\"NP\",\"message\":\"Authorization Failed.\"}");
    }
}
