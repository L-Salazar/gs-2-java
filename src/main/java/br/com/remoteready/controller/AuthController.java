package br.com.remoteready.controller;

import br.com.remoteready.model.Usuario;
import br.com.remoteready.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nome,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            // Verificar se o email já existe
            // (você pode adicionar um método no repository para isso)

            // Criar novo usuário
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nome);
            usuario.setEmail(email);
            usuario.setPassword(password); // Em produção, use BCrypt para criptografar
            usuario.setRole(role); // ADMIN ou OPERADOR
            usuario.setDataCriacao(LocalDateTime.now());

            // Salvar usuário
            usuarioService.salvar(usuario);

            // Mensagem de sucesso
            redirectAttributes.addFlashAttribute("sucesso", "Conta criada com sucesso! Faça login para continuar.");
            return "redirect:/login";

        } catch (Exception e) {
            // Mensagem de erro
            model.addAttribute("erro", "Erro ao criar conta: " + e.getMessage());
            return "auth/register";
        }
    }
}