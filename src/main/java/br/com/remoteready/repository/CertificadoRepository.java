package br.com.remoteready.repository;

import br.com.remoteready.model.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    // Todos os certificados de um usuário (caso queira histórico)
    List<Certificado> findByIdUsuario(Long idUsuario);

    // Último certificado emitido (caso reaproveite)
    Optional<Certificado> findFirstByIdUsuarioOrderByDataEmissaoDesc(Long idUsuario);

    // Saber se já existe pelo menos um
    boolean existsByIdUsuario(Long idUsuario);
}