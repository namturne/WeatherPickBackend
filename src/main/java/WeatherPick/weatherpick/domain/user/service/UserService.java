package WeatherPick.weatherpick.domain.user.service;

import WeatherPick.weatherpick.common.ResponseDto;
import WeatherPick.weatherpick.domain.user.dto.SignRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserInfoResponseDto;
import WeatherPick.weatherpick.domain.user.dto.UserRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserResponseDto;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.entity.UserRoleType;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

     // @param sessionUsername - 토큰에서 꺼낸 현재 로그인된 username
     // @param targetUsername  - 조회/수정/삭제하려는 대상 username

    public Boolean isAccess(String sessionUsername, String targetUsername) {
        if (sessionUsername == null) return false;

        String sessionRole = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().iterator().next().getAuthority();

        // ADMIN 권한이면 무조건 허용
        if ("ROLE_ADMIN".equals(sessionRole)) {
            return true;
        }
        // 아니면 sessionUsername이 targetUsername과 일치해야 허용
        return sessionUsername.equals(targetUsername);
    }


     //아이디 중복 확인

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


     //이메일 중복 확인

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkPassword(String username, String rawPassword) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return false;
        return bCryptPasswordEncoder.matches(rawPassword, user.get().getPassword());
    }

    // 유저 생성
    @Transactional
    public void createOneUser(SignRequestDto dto) {
        String username    = dto.getUsername();
        String password    = dto.getPassword();
        String nickname    = dto.getNickname();
        String email       = dto.getEmail();
        String name        = dto.getName();
        String phonenumber = dto.getPhonenumber();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        entity.setNickname(nickname);
        entity.setRole(UserRoleType.USER);
        entity.setEmail(email);
        entity.setName(name);
        entity.setPhoneNumber(phonenumber);

        userRepository.save(entity);
    }

    // 유저 한 명 조회
    @Transactional(readOnly = true)
    public UserResponseDto readOneUser(String username) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(entity.getUsername());
        dto.setNickname(entity.getNickname());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole().toString());
        dto.setName(entity.getName());
        dto.setPhonenumber(entity.getPhoneNumber());
        dto.setCreatedate(entity.getCreatedate());
        return dto;
    }

    // 내 정보(토큰에서 꺼낸 username) 조회
    public ResponseEntity<? super UserInfoResponseDto> getInfoUser(String username) {
        Optional<UserEntity> userEntity = Optional.empty();
        try {
            userEntity = userRepository.findByUsername(username);
            if (userEntity.isEmpty()) {
                return UserInfoResponseDto.notExistUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return UserInfoResponseDto.success(userEntity.get());
    }

    // 유저 수정
    @Transactional
    public void updateOneUser(UserRequestDto dto, String username) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        if (dto.getNickname() != null && !dto.getNickname().isEmpty()) {
            entity.setNickname(dto.getNickname());
        }
        userRepository.save(entity);
    }

    // 유저 삭제
    @Transactional
    public void deleteOneUser(String username) {
        userRepository.deleteByUsername(username);
    }

    // 스프링 시큐리티 로그인 처리용
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + username));
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRole().toString())
                .build();
    }
}
