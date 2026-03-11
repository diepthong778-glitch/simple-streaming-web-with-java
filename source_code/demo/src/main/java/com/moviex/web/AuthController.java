package com.moviex.web;

import com.moviex.user.UserAccount;
import com.moviex.user.UserService;
import com.moviex.web.dto.LoginForm;
import com.moviex.web.dto.RegisterForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("loginForm") LoginForm form,
                          BindingResult bindingResult,
                          Model model,
                          HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        return userService.authenticate(form.getUsername(), form.getPassword())
                .map(user -> {
                    session.setAttribute("username", user.getUsername());
                    return "redirect:/home";
                })
                .orElseGet(() -> {
                    model.addAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
                    return "login";
                });
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registration";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("registerForm") RegisterForm form,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        UserAccount user = new UserAccount();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setSubscription(form.getSubscription());

        boolean ok = userService.register(user);
        if (!ok) {
            model.addAttribute("registerError", "Email hoặc username đã tồn tại");
            return "registration";
        }
        model.addAttribute("registerSuccess", "Đăng ký thành công, hãy đăng nhập");
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
