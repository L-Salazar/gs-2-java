package br.com.remoteready.service;

import br.com.remoteready.model.Usuario;
import br.com.remoteready.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNomeUsuario(dados.getNomeUsuario());
        existente.setEmail(dados.getEmail());
        existente.setPassword(dados.getPassword());
        existente.setRole(dados.getRole());
        existente.setDataCriacao(dados.getDataCriacao());
        return usuarioRepository.save(existente);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario validarCredenciais(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
    }
}