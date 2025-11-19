package br.com.remoteready.service;

import br.com.remoteready.model.UserPost;
import br.com.remoteready.model.Usuario;
import br.com.remoteready.model.BlogPost;
import br.com.remoteready.repository.UserPostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;

    public UserPostService(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    public List<UserPost> listarTodos() {
        return userPostRepository.findAll();
    }

    public List<UserPost> listarPorUsuario(Long idUsuario) {
        return userPostRepository.findByUsuarioId(idUsuario);
    }

    public UserPost buscarPorId(Long id) {
        return userPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserPost não encontrado: " + id));
    }

    @Transactional
    public UserPost salvar(UserPost userPost) {
        if (userPost.getStatus() == null || userPost.getStatus().isBlank()) {
            userPost.setStatus("READ");
        }
        if (userPost.getDataLeitura() == null) {
            userPost.setDataLeitura(LocalDateTime.now());
        }
        return userPostRepository.save(userPost);
    }

    @Transactional
    public void excluir(Long id) {
        if (!userPostRepository.existsById(id)) {
            throw new IllegalArgumentException("UserPost não encontrado: " + id);
        }
        userPostRepository.deleteById(id);
    }

    /**
     * Marca um post como lido para um usuário.
     * Evita duplicar registros se já existir.
     */
    @Transactional
    public UserPost marcarComoLido(Usuario usuario, BlogPost blogPost) {
        return userPostRepository.findByUsuarioIdAndBlogPostId(usuario.getId(), blogPost.getId())
                .orElseGet(() -> {
                    UserPost novo = new UserPost();
                    novo.setUsuario(usuario);
                    novo.setBlogPost(blogPost);
                    novo.setStatus("READ");
                    novo.setDataLeitura(LocalDateTime.now());
                    return userPostRepository.save(novo);
                });
    }

    /**
     * Verifica se o usuário já leu determinado post.
     */
    public boolean jaLeuPost(Long idUsuario, Long idPost) {
        return userPostRepository.existsByUsuarioIdAndBlogPostId(idUsuario, idPost);
    }

    /**
     * Lista apenas os IDs dos posts lidos por um usuário (status = 'READ').
     */
    public List<Long> listarIdsPostsLidosPorUsuario(Long idUsuario) {
        return userPostRepository.findByUsuarioIdAndStatus(idUsuario, "READ")
                .stream()
                .map(up -> up.getBlogPost().getId())
                .toList();
    }

    /**
     * Conta quantos posts o usuário leu (status = 'READ').
     */
    public long contarPostsLidosPorUsuario(Long idUsuario) {
        return userPostRepository.countByUsuarioIdAndStatus(idUsuario, "READ");
    }
}
