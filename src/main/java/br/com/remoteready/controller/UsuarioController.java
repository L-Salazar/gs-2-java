package br.com.remoteready.controller;

import br.com.remoteready.model.Usuario;
import br.com.remoteready.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listagem
    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = usuarioService.listar();
        model.addAttribute("usuarios", usuarios);
        return "users/listagem";
    }

    // Formulário - novo
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", List.of("ADMIN", "OPERADOR", "USER"));
        return "users/formulario";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute @Valid Usuario usuario,
                         org.springframework.validation.BindingResult binding,
                         Model model) {
        model.addAttribute("roles", List.of("ADMIN", "OPERADOR", "USER"));
        if (binding.hasErrors()) {
            return "users/formulario";
        }
        usuarioService.salvar(usuario);
        return "redirect:/users";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", List.of("ADMIN", "OPERADOR", "USER"));
        return "users/formulario";
    }

    // Atualizar
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute @Valid Usuario usuario,
                          org.springframework.validation.BindingResult binding,
                          Model model) {
        model.addAttribute("roles", List.of("ADMIN", "OPERADOR", "USER"));
        if (binding.hasErrors()) {
            return "users/formulario";
        }
        usuarioService.atualizar(id, usuario);
        return "redirect:/users";
    }

    // Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return "redirect:/users";
    }
}