package dev.auth.service;

import dev.auth.dto.UserRegistrationDTO;
import dev.auth.model.User;
import dev.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(UserRegistrationDTO userRegistrationDTO) {
        if(!userRepository.existsByUsername(userRegistrationDTO.getUsername())){
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());

            // User 객체 생성
            User newUser = new User();
            newUser.setUsername(userRegistrationDTO.getUsername());
            newUser.encodePassword(encodedPassword);

            // User 저장
            userRepository.save(newUser);
        } else{
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }
}
