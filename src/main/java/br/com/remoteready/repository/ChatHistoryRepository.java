package br.com.remoteready.repository;

import br.com.remoteready.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    // Histórico de um usuário (mais recente primeiro)
    List<ChatHistory> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);

    List<ChatHistory> findByUsuarioIdOrderByIdDesc(Long usuarioId);
}
