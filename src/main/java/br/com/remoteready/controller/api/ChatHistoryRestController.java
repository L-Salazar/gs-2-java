package br.com.remoteready.controller.api;

import br.com.remoteready.model.ChatHistory;
import br.com.remoteready.model.Usuario;
import br.com.remoteready.service.ChatHistoryService;
import br.com.remoteready.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-history")
@CrossOrigin(origins = "*")
public class ChatHistoryRestController {

    private final ChatHistoryService chatHistoryService;
    private final UsuarioService usuarioService;

    public ChatHistoryRestController(ChatHistoryService chatHistoryService,
                                     UsuarioService usuarioService) {
        this.chatHistoryService = chatHistoryService;
        this.usuarioService = usuarioService;
    }

    // GET /api/chat-history/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long userId) {
        try {
            // valida existência do usuário
            Usuario usuario = usuarioService.buscarPorId(userId);

            List<ChatHistory> historico = chatHistoryService.listarPorUsuario(usuario.getId());
            return ResponseEntity.ok(historico);

        } catch (IllegalArgumentException e) {
            // usuário não existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("USER_NOT_FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("SERVER_ERROR", "Erro ao buscar histórico"));
        }
    }

    // POST /api/chat-history
    // Body JSON:
    // {
    //   "userId": 1,
    //   "prompt": "pergunta do usuário",
    //   "response": "resposta da IA"
    // }
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody ChatMessageRequest request) {
        try {
            Usuario usuario = usuarioService.buscarPorId(request.getUserId());

            ChatHistory saved = chatHistoryService.registrarMensagem(
                    usuario,
                    request.getPrompt(),
                    request.getResponse()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("USER_NOT_FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("SERVER_ERROR", "Erro ao salvar histórico de chat"));
        }
    }

    // DTOs

    public static class ChatMessageRequest {
        private Long userId;
        private String prompt;
        private String response;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}
