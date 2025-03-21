package WeatherPick.weatherpick.domain.user.service;

import WeatherPick.weatherpick.domain.user.dto.UserRequestDto;
import WeatherPick.weatherpick.domain.user.dto.UserResponseDto;
import WeatherPick.weatherpick.domain.user.entity.UserEntity;
import WeatherPick.weatherpick.domain.user.entity.UserRoleType;
import WeatherPick.weatherpick.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //유저 한 명 생성
    @Transactional
    public void createOneUser(UserRequestDto dto){
        String username = dto.getUsername();
        String password = dto.getPassword();
        String nickname = dto.getNickname();
        String email = dto.getEmail();

        // 중복 username이 있는지 확인
        if (userRepository.existsByUsername(username)) {
            return;
        }
        // 중복 email이 있는지 확인
        if (userRepository.existsByEmail(email)) {
            return;
        }


        // 유저에 대한 Entity 생성 : DTO -> Entity 및 추가 정보 set
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        entity.setNickname(nickname);
        entity.setRole(UserRoleType.USER);
        entity.setEmail(email);

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

        return dto;
    }

    // 유저 로그인 (스프링 시큐리티 형식)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUsername(username).orElseThrow();
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRole().toString())
                .build();
    }
}
