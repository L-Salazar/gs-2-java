package br.com.remoteready.controller.api;

import br.com.remoteready.model.BlogPost;
import br.com.remoteready.service.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Permitir requisições do mobile
public class BlogPostRestController {

    private final BlogPostService blogPostService;

    public BlogPostRestController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // GET /api/posts - Listar todos
    @GetMapping
    public ResponseEntity<List<BlogPost>> listar() {
        List<BlogPost> posts = blogPostService.listar();
        return ResponseEntity.ok(posts);
    }

    // GET /api/posts/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> buscarPorId(@PathVariable Long id) {
        try {
            BlogPost post = blogPostService.buscarPorId(id);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/posts - Criar novo
    @PostMapping
    public ResponseEntity<BlogPost> criar(@RequestBody BlogPost blogPost) {
        try {
            BlogPost novoPost = blogPostService.salvar(blogPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/posts/{id} - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> atualizar(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        try {
            BlogPost postAtualizado = blogPostService.atualizar(id, blogPost);
            return ResponseEntity.ok(postAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/posts/{id} - Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            blogPostService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}