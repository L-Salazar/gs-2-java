package br.com.remoteready.controller;

import br.com.remoteready.model.BlogPost;
import br.com.remoteready.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // Listagem
    @GetMapping
    public String listar(Model model) {
        List<BlogPost> posts = blogPostService.listar();
        model.addAttribute("posts", posts);
        return "posts/listagem";
    }

    // Formulário - novo
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        model.addAttribute("tags", List.of("Tecnologia", "Trabalho Remoto", "Produtividade", "Carreira", "Dicas", "Notícias"));
        return "posts/formulario";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute @Valid BlogPost blogPost,
                         org.springframework.validation.BindingResult binding,
                         Model model) {
        model.addAttribute("tags", List.of("Tecnologia", "Trabalho Remoto", "Produtividade", "Carreira", "Dicas", "Notícias"));
        if (binding.hasErrors()) {
            return "posts/formulario";
        }
        blogPostService.salvar(blogPost);
        return "redirect:/posts";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        BlogPost blogPost = blogPostService.buscarPorId(id);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("tags", List.of("Tecnologia", "Trabalho Remoto", "Produtividade", "Carreira", "Dicas", "Notícias"));
        return "posts/formulario";
    }

    // Atualizar
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute @Valid BlogPost blogPost,
                          org.springframework.validation.BindingResult binding,
                          Model model) {
        model.addAttribute("tags", List.of("Tecnologia", "Trabalho Remoto", "Produtividade", "Carreira", "Dicas", "Notícias"));
        if (binding.hasErrors()) {
            return "posts/formulario";
        }
        blogPostService.atualizar(id, blogPost);
        return "redirect:/posts";
    }

    // Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        blogPostService.excluir(id);
        return "redirect:/posts";
    }
}