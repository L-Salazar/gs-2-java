package br.com.remoteready.service;

import br.com.remoteready.model.Usuario;
import br.com.remoteready.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscando o usuário no banco de dados
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Retorna o UserDetails com a senha (sem criptografia por enquanto)
        return User.builder()
                .username(usuario.getEmail())  // E-mail como nome de usuário
                .password("{noop}" + usuario.getPassword())  // {noop} indica que não há criptografia
                .roles(usuario.getRole())      // Tipo de usuário (ADMIN, OPERADOR, etc)
                .build();
    }

    // Método para salvar o usuário (sem criptografia da senha)
    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);  // Senha será salva como está, sem criptografia
    }
}