package br.com.remoteready.controller.api;

import br.com.remoteready.model.response.ErrorResponse;
import br.com.remoteready.model.Usuario;
import br.com.remoteready.model.response.LoginResponse;
import br.com.remoteready.model.response.LoginRequest;
import br.com.remoteready.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Permitir requisições do mobile
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/users - Listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/users/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/users - Criar novo
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/users/{id} - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/users/{id} - Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            usuarioService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/users/role/{role} - Listar por role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Usuario>> listarPorRole(@PathVariable String role) {
        List<Usuario> usuarios = usuarioService.listar()
                .stream()
                .filter(u -> role.equalsIgnoreCase(u.getRole()))
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.validarCredenciais(request.getEmail(), request.getPassword());

            LoginResponse response = new LoginResponse(
                    usuario.getId(),
                    usuario.getNomeUsuario(),
                    usuario.getEmail(),
                    usuario.getRole(),
                    "123abc"
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // credenciais inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("INVALID_CREDENTIALS", "E-mail ou senha inválidos"));
        } catch (Exception e) {
            // erro inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("SERVER_ERROR", "Erro ao processar login"));
        }
    }


}