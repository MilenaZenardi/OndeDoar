package com.ondedoar.cotroller;

import com.ondedoar.dto.AuthenticationDTO;
import com.ondedoar.dto.UserDTO;
import com.ondedoar.exception.ValidationUtils;
import com.ondedoar.model.UserModel;
import com.ondedoar.service.TokenService;
import com.ondedoar.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid AuthenticationDTO authenticationDTO, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            if (auth.isAuthenticated()) {

                // Se a autenticação for bem-sucedida, gere um token e armazene-o de forma apropriada, como em uma sessão ou cookie
                var token = tokenService.generateToken((UserModel) auth.getPrincipal());

                Cookie cookie = new Cookie("authToken", token);
                cookie.setMaxAge(3600); // Tempo de vida do cookie em segundos (por exemplo, 1 hora)
                cookie.setPath("/"); // Define o caminho do cookie como raiz para que seja acessível em todo o aplicativo
                response.addCookie(cookie);

                return "redirect:/";
            } else {
                // Se a autenticação falhar, você pode adicionar uma mensagem de erro à página de login, se necessário
                redirectAttributes.addFlashAttribute("errorMessage", "Autenticação falhou.");

                return "redirect:/auth/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Login ou senha inválido.");

            return "redirect:/auth/login";
        }
    }

    @GetMapping("/create")
    public String create() {
        return "auth/form";
    }

    @PostMapping("/create")
    public String createUser(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = ValidationUtils.getErrorMessages(bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
            redirectAttributes.addFlashAttribute("user", userDTO );
            return "redirect:/auth/create";
        }

        try {
            String login = userDTO.login();

            if (userService.findByLogin(login)) {
                // Se o login já existir, adicione uma mensagem de erro e redirecione de volta para o formulário de registro
                redirectAttributes.addFlashAttribute("errorMessage", "O login já existe. Escolha outro login.");
                redirectAttributes.addFlashAttribute("user", userDTO );
                return "redirect:/auth/create"; // Redireciona para a página de registro
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(userDTO, userModel);
            userService.save(userModel, encryptedPassword);


            // Adicione uma mensagem de sucesso e redirecione para a página de login
            redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
            return "redirect:/auth/login"; // Redireciona para a página de login
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            redirectAttributes.addFlashAttribute("user", userDTO );
            return "redirect:/auth/create";
        }
    }
}
