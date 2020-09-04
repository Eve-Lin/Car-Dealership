package com.evelin.cars.web;

import com.evelin.cars.model.Role;
import com.evelin.cars.model.User;
import com.evelin.cars.repository.RoleRepository;
import com.evelin.cars.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthService authService, RoleRepository roleRepository) {
        this.authService = authService;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/register")
    public String getAuthForm(Model registration) {
        registration.addAttribute("user", new User());
        System.out.println(roleRepository.findAll());
        registration.addAttribute("userRoles", roleRepository.findAll().stream().filter( distinctByKey(r -> r.getName()) )
                .collect( Collectors.toList() ));
        registration.addAttribute("userRole", new Role());
        return "auth-register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") User user,
                                  @ModelAttribute("userRole") Role userRole,
                                  final BindingResult result,
                                  RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            log.error("Errors in user: ", result.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", result);
            return "redirect:register";
        }else {
            try {
                User registeredUser = authService.register(user);
                return "redirect:login";
            } catch (Exception ex) {
                log.error("Error registering user", ex);
                redirectAttributes.addFlashAttribute("user", user);
                redirectAttributes.addFlashAttribute("errors", result);
                return "redirect:register";
            }
        }
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if (model.getAttribute("username") == null) {
            model.addAttribute("username", "");
        }
        if (model.getAttribute("password") == null) {
            model.addAttribute("password", "");
        }
        return "auth-login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/formLoginSuccess")
    public String getFormLoginInfo(Model model, @AuthenticationPrincipal Authentication authentication) {
        System.out.println("Database user logged successfully");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "home";

    }
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
