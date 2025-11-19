package br.com.remoteready.service;

import br.com.remoteready.model.ChatHistory;
import br.com.remoteready.model.Usuario;
import br.com.remoteready.repository.ChatHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;

    public ChatHistoryService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public List<ChatHistory> listarTodos() {
        return chatHistoryRepository.findAll();
    }

    public List<ChatHistory> listarPorUsuario(Long idUsuario) {
        return chatHistoryRepository.findByUsuarioIdOrderByDataCriacaoDesc(idUsuario);
    }

    public ChatHistory buscarPorId(Long id) {
        return chatHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ChatHistory não encontrado: " + id));
    }

    @Transactional
    public ChatHistory salvar(ChatHistory chatHistory) {
        return chatHistoryRepository.save(chatHistory);
    }

    @Transactional
    public void excluir(Long id) {
        if (!chatHistoryRepository.existsById(id)) {
            throw new IllegalArgumentException("ChatHistory não encontrado: " + id);
        }
        chatHistoryRepository.deleteById(id);
    }

    @Transactional
    public ChatHistory registrarMensagem(Usuario usuario, String prompt, String response) {
        ChatHistory ch = new ChatHistory();
        ch.setUsuario(usuario);
        ch.setPrompt(prompt);
        ch.setResponse(response);
        return chatHistoryRepository.save(ch);
    }
}
