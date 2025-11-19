package br.com.remoteready.controller.api;

import br.com.remoteready.model.Certificado;
import br.com.remoteready.model.response.ErrorResponse;
import br.com.remoteready.service.CertificadoService;
import br.com.remoteready.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CertificadoRestController {

    private final CertificadoService certificadoService;
    private final UsuarioService usuarioService;

    public CertificadoRestController(CertificadoService certificadoService,
                                     UsuarioService usuarioService) {
        this.certificadoService = certificadoService;
        this.usuarioService = usuarioService;
    }

    // GET /api/certificates/user/1
    // lista todos os certificados do usuário (se quiser histórico)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Certificado>> listarPorUsuario(@PathVariable Long userId) {
        // garante que o usuário existe
        usuarioService.buscarPorId(userId);
        List<Certificado> lista = certificadoService.listarPorUsuario(userId);
        return ResponseEntity.ok(lista);
    }

    // GET /api/certificates/user/1/latest
    // busca o último certificado do usuário (mais usado no mobile)
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<Certificado> buscarUltimo(@PathVariable Long userId) {
        usuarioService.buscarPorId(userId);
        Certificado cert = certificadoService.buscarUltimoPorUsuario(userId);
        if (cert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cert);
    }

    // POST /api/certificates/generate?userId=1
    // tenta gerar certificado; se já existir, só devolve o existente
    @PostMapping("/generate")
    public ResponseEntity<?> gerar(@RequestParam("userId") Long userId) {
        try {
            usuarioService.buscarPorId(userId); // valida existência
            Certificado cert = certificadoService.gerarCertificadoSeElegivel(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(cert);
        } catch (IllegalArgumentException e) {
            // usuário não existe
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // não elegível (menos de 10 posts lidos)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("NOT_ELIGIBLE", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
