package br.com.remoteready.service;

import br.com.remoteready.model.Certificado;
import br.com.remoteready.repository.CertificadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final UserPostService userPostService;

    public CertificadoService(CertificadoRepository certificadoRepository,
                              UserPostService userPostService) {
        this.certificadoRepository = certificadoRepository;
        this.userPostService = userPostService;
    }

    public List<Certificado> listarPorUsuario(Long idUsuario) {
        return certificadoRepository.findByIdUsuario(idUsuario);
    }

    public Certificado buscarUltimoPorUsuario(Long idUsuario) {
        return certificadoRepository.findFirstByIdUsuarioOrderByDataEmissaoDesc(idUsuario)
                .orElse(null);
    }

    /**
     * Gera um certificado se o usuário tiver lido pelo menos requiredCount posts.
     * Se já existir certificado, apenas retorna o último.
     */
    @Transactional
    public Certificado gerarCertificadoSeElegivel(Long idUsuario, long requiredCount) {
        // Se já tem certificado, reaproveita
        var existente = certificadoRepository
                .findFirstByIdUsuarioOrderByDataEmissaoDesc(idUsuario);
        if (existente.isPresent()) {
            return existente.get();
        }

        long lidos = userPostService.contarPostsLidosPorUsuario(idUsuario);
        if (lidos < requiredCount) {
            throw new IllegalStateException(
                    "Usuário ainda não elegível. Lidos: " + lidos + ", necessário: " + requiredCount
            );
        }

        Certificado cert = new Certificado();
        cert.setIdUsuario(idUsuario);
        cert.setDataEmissao(LocalDate.now());
        cert.setTitulo("Remote Work Ready – Trilha Essencial");

        return certificadoRepository.save(cert);
    }

    /**
     * Versão com regra fixa de 10 posts.
     */
    @Transactional
    public Certificado gerarCertificadoSeElegivel(Long idUsuario) {
        return gerarCertificadoSeElegivel(idUsuario, 10L);
    }
}
