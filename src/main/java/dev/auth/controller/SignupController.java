package dev.auth.controller;

import dev.auth.dto.UserRegistrationDTO;
import dev.auth.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignupController {
    private final SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @RequestMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO()); // 빈 객체 전달
        return "signup"; // 회원가입 폼을 렌더링합니다.
    }

    @PostMapping("/signup")
    public String registerUser(UserRegistrationDTO userRegistrationDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";  // 유효성 검사 실패시 폼으로 돌아가기
        }

        // 비밀번호 확인이 일치하는지 체크
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            model.addAttribute("passwordError", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "signup";
        }

        try {
            signupService.signup(userRegistrationDTO); // 회원가입 처리
        } catch (IllegalArgumentException e) {
            model.addAttribute("usernameError", e.getMessage()); // 중복 아이디 오류 처리
            return "signup";
        }

        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

    @PostMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam String username) {
        return !signupService.isUsernameTaken(username); // 중복된 아이디가 있으면 false 반환
    }
}
