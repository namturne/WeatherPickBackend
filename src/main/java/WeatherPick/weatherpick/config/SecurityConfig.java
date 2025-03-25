package WeatherPick.weatherpick.config;


import WeatherPick.weatherpick.domain.user.entity.UserRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 스프링 시큐리티 암호화 클래스
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 시큐리티 role 수직 계층 적용
    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role(UserRoleType.ADMIN.toString()).implies(UserRoleType.USER.toString())

                .build();
    }
    // 시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // csrf 보안 해제
        http
                .csrf(csrf -> csrf.disable());

        // 접근 경로별 인가 설정
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/user/join").permitAll() // login, /user/join 경로는 모든 사용자에게 허용
                        .requestMatchers("/user/update/**").hasRole("USER") // /user/update/** 경로는 "USER" 역할을 가진 사용자만 허용.
                        .anyRequest().authenticated()
                );
        // 로그인 방식 설정 Form 로그인 방식
        http
                .formLogin(login -> login
                        .loginPage("/login") // 커스텀 로그인 페이지 지정
                        .defaultSuccessUrl("/main") // 로그인 성공 시 이동할 페이지
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 엔드포인트 설정
                        .logoutSuccessUrl("/login")  // 로그아웃 후 이동할 페이지
                        .invalidateHttpSession(true) // 세션 삭제
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );

        return http.build();
    }

}
