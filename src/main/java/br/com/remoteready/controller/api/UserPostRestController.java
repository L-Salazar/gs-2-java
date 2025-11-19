package br.com.remoteready.controller.api;

import br.com.remoteready.model.BlogPost;
import br.com.remoteready.model.UserPost;
import br.com.remoteready.model.Usuario;
import br.com.remoteready.service.BlogPostService;
import br.com.remoteready.service.UserPostService;
import br.com.remoteready.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-posts")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserPostRestController {

    private final UserPostService userPostService;
    private final UsuarioService usuarioService;
    private final BlogPostService blogPostService;

    public UserPostRestController(UserPostService userPostService,
                                  UsuarioService usuarioService,
                                  BlogPostService blogPostService) {
        this.userPostService = userPostService;
        this.usuarioService = usuarioService;
        this.blogPostService = blogPostService;
    }

    // POST /api/user-posts/mark-read?userId=1&postId=10
    @PostMapping("/mark-read")
    public ResponseEntity<UserPost> marcarComoLido(
            @RequestParam("userId") Long userId,
            @RequestParam("postId") Long postId) {

        try {
            Usuario usuario = usuarioService.buscarPorId(userId);
            BlogPost blogPost = blogPostService.buscarPorId(postId);

            UserPost userPost = userPostService.marcarComoLido(usuario, blogPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(userPost);

        } catch (IllegalArgumentException e) {
            // Usuário ou post não encontrado
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Erro genérico
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/user-posts/read-ids?userId=1
    // Retorna apenas os IDs dos posts que o usuário já leu
    @GetMapping("/read-ids")
    public ResponseEntity<List<Long>> listarIdsPostsLidos(@RequestParam("userId") Long userId) {
        try {
            // Garante que o usuário existe (se não existir, lança IllegalArgumentException)
            usuarioService.buscarPorId(userId);

            List<Long> ids = userPostService.listarIdsPostsLidosPorUsuario(userId);
            return ResponseEntity.ok(ids);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/user-posts/certificate-eligibility?userId=1
    // Verifica se o usuário pode gerar certificado (>= 10 posts lidos)
    @GetMapping("/certificate-eligibility")
    public ResponseEntity<CertificateEligibilityResponse> verificarElegibilidadeCertificado(
            @RequestParam("userId") Long userId) {

        try {
            usuarioService.buscarPorId(userId); // valida existência

            long requiredCount = 10L;
            long readCount = userPostService.contarPostsLidosPorUsuario(userId);
            boolean eligible = readCount >= requiredCount;

            CertificateEligibilityResponse response =
                    new CertificateEligibilityResponse(userId, readCount, requiredCount, eligible);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DTO de resposta para elegibilidade do certificado
    public static class CertificateEligibilityResponse {
        private Long userId;
        private long readCount;
        private long requiredCount;
        private boolean eligible;

        public CertificateEligibilityResponse(Long userId, long readCount, long requiredCount, boolean eligible) {
            this.userId = userId;
            this.readCount = readCount;
            this.requiredCount = requiredCount;
            this.eligible = eligible;
        }

        public Long getUserId() {
            return userId;
        }

        public long getReadCount() {
            return readCount;
        }

        public long getRequiredCount() {
            return requiredCount;
        }

        public boolean isEligible() {
            return eligible;
        }
    }
}
