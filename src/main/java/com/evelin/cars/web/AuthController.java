package com.evelin.cars.web;

import com.evelin.cars.model.User;
import com.evelin.cars.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/register")
    public String getAuthForm(@ModelAttribute("user") User user) {
        return "auth-register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            log.error("Error registering user: ", result.getAllErrors());
            return "auth-register";
        }
        try {
            User registeredUser = authService.register(user);
            return "redirect:login";
        } catch (Exception ex) {
            log.error("Error registering user", ex);
            return "auth-result";
        }
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if(model.getAttribute("username") == null){
            model.addAttribute("usernmae","");
        }
        if(model.getAttribute("password") == null){
            model.addAttribute("password","");
        }
        return "auth-login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @ModelAttribute("redirectUrl") String redirectUrl,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {

        User loggedUser = authService.login(username, password);
        if(loggedUser == null){
            String errors = "Invalid username or password.";
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:login";
        }else{
            session.setAttribute("user", loggedUser);
            if(redirectUrl != null && redirectUrl.trim().length() > 0){
                return "redirect:"+ redirectUrl;
            }else{
                return "redirect:" + redirectUrl;
            }
        }
    }

}
