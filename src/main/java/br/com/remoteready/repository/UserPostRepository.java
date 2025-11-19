package br.com.remoteready.repository;

import br.com.remoteready.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPostRepository extends JpaRepository<UserPost, Long> {

    // Lista todos os posts (UserPost) de um usuário
    List<UserPost> findByUsuarioId(Long usuarioId);

    // Lista apenas registros com um status específico (ex.: 'READ')
    List<UserPost> findByUsuarioIdAndStatus(Long usuarioId, String status);

    // Verifica se já existe um registro de leitura para esse usuário + post
    boolean existsByUsuarioIdAndBlogPostId(Long usuarioId, Long blogPostId);

    // Busca o registro específico de leitura
    Optional<UserPost> findByUsuarioIdAndBlogPostId(Long usuarioId, Long blogPostId);

    // Conta quantos posts o usuário leu (por status)
    long countByUsuarioIdAndStatus(Long usuarioId, String status);
}
