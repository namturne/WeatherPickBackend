package WeatherPick.weatherpick.domain.user.service;

import WeatherPick.weatherpick.domain.user.dto.SignRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserResponseDto;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.entity.UserRoleType;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 유저 접근 권한 체크
    public Boolean isAccess(String username) {

        // 현재 로그인 되어 있는 유저의 username
        String sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // 현재 로그인 되어 있는 유저의 role
        String sessionRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();

        // 수직적으로 ADMIN이면 무조건 접근 가능
        if ("ROLE_ADMIN".equals(sessionRole)) {
            return true;
        }

        // 수평적으로 특정 행위를 수행할 username에 대해 세션(현재 로그인한) username과 같은지
        if (username.equals(sessionUsername)) {
            return true;
        }

        // 나머지 다 불가
        return false;
    }


    public boolean checkPassword(String username, String rawPassword) {
        // 사용자의 유저 정보 조회
        Optional<UserEntity> user = userRepository.findByUsername(username);

        // 유저가 없거나 비밀번호가 일치하지 않으면 false 반환
        if (user.isEmpty()) {
            return false;
        }

        // 저장된 암호화된 비밀번호와 사용자가 입력한 평문 비밀번호를 비교
        return bCryptPasswordEncoder.matches(rawPassword, user.get().getPassword());
    }

    //유저 한 명 생성
    @Transactional
    public void createOneUser(SignRequestDto dto){
        String username = dto.getUsername();
        String password = dto.getPassword();
        String nickname = dto.getNickname();
        String email = dto.getEmail();
        String name = dto.getName();
        String phonenumber = dto.getPhonenumber();

        // 중복 username이 있는지 확인
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        // 중복 email이 있는지 확인
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }


        // 유저에 대한 Entity 생성 : DTO -> Entity 및 추가 정보 set
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        entity.setNickname(nickname);
        entity.setRole(UserRoleType.USER);
        entity.setEmail(email);
        entity.setName(name);
        entity.setPhoneNumber(phonenumber);

        //유저 저장
        userRepository.save(entity);
    }

    //유저 한 명 읽기
    @Transactional(readOnly = true)
    public UserResponseDto readOneUser(String username){
        UserEntity entity = userRepository.findByUsername(username).orElseThrow();

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

    // 유저 모두 읽기
    @Transactional(readOnly = true)
    public List<UserResponseDto> readAllUsers() {

        List<UserEntity> list = userRepository.findAll();

        List<UserResponseDto> dtos = new ArrayList<>();
        for (UserEntity user : list) {
            UserResponseDto dto = new UserResponseDto();
            dto.setUsername(user.getUsername());
            dto.setNickname(user.getNickname());
            dto.setRole(user.getRole().toString());
            dto.setName(user.getName());
            dto.setPhonenumber(user.getPhoneNumber());
            dto.setCreatedate(user.getCreatedate());
            dtos.add(dto);
        }

        return dtos;
    }

    // 유저 한 명 수정
    @Transactional
    public void updateOneUser(UserRequestDto dto, String username) {

        // 기존 유저 정보 읽기
        UserEntity entity = userRepository.findByUsername(username).orElseThrow();

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }

        if (dto.getNickname() != null && !dto.getNickname().isEmpty()) {
            entity.setNickname(dto.getNickname());
        }

        userRepository.save(entity);
    }

    // 유저 한 명 삭제
    @Transactional
    public void deleteOneUser(String username) {
        userRepository.deleteByUsername(username);
    }

    // 유저 로그인 (스프링 시큐리티 형식)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRole().toString())
                .build();
    }


}
