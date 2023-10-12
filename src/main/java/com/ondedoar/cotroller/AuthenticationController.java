package com.ondedoar.cotroller;

import com.ondedoar.dto.AuthenticationDTO;
import com.ondedoar.dto.LoginResponseDTO;
import com.ondedoar.dto.RegisterDTO;
import com.ondedoar.model.UserModel;
import com.ondedoar.repository.UserRepository;
import com.ondedoar.service.TokenService;
import com.ondedoar.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/index")
    public String index() {
        return "index/teste";
    }


    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid AuthenticationDTO authenticationDTO, HttpServletRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        if (auth.isAuthenticated()) {

            // Se a autenticação for bem-sucedida, gere um token e armazene-o de forma apropriada, como em uma sessão ou cookie
            var token = tokenService.generateToken((UserModel) auth.getPrincipal());

            // Redirecione o usuário para a página de sucesso ou página inicial
            return "redirect:/auth/index";
        } else {
            // Se a autenticação falhar, você pode adicionar uma mensagem de erro à página de login, se necessário

            // Redirecione o usuário de volta para a página de login com uma mensagem de erro
            return "redirect:/auth/login"; // Substitua "/auth/login" pela URL da sua página de login
        }
    }

    @GetMapping("/register")
    public String register() {
        return "auth/registrar";
    }


    @PostMapping("/register")
    public String registerUser(@Valid RegisterDTO registerDTO, RedirectAttributes redirectAttributes) {
        String login = registerDTO.login();

        if (userService.findByLogin(login)) {
            // Se o login já existir, adicione uma mensagem de erro e redirecione de volta para o formulário de registro
            redirectAttributes.addFlashAttribute("errorMessage", "O login já existe. Escolha outro login.");
            return "redirect:/auth/register"; // Redireciona para a página de registro
        }

        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(registerDTO, userModel);
            userService.save(userModel, encryptedPassword);
        }catch (ConstraintViolationException e) {
            // Lidere com as violações de validação aqui
            // Por exemplo, obtenha as mensagens de erro e adicione-as ao atributo "errorMessage"
            String errorMessage = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .findFirst()
                    .orElse("Erro de validação desconhecido");
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/auth/register"; // Redireciona para a página de registro com a mensagem de erro
        }

        // Adicione uma mensagem de sucesso e redirecione para a página de login
        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/auth/login"; // Redireciona para a página de login
    }
}
