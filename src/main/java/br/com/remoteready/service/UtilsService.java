package br.com.remoteready.service;

import br.com.remoteready.dto.ChatHistoryResult;
import br.com.remoteready.dto.RelatorioElegibilidadeDTO;
import br.com.remoteready.repository.UtilsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilsService {

    private final UtilsRepository utilsRepository;

    public UtilsService(UtilsRepository utilsRepository) {
        this.utilsRepository = utilsRepository;
    }

    public Optional<ChatHistoryResult> obterHistoricoUsuario(Long idUsuario) {
        return utilsRepository.obterHistoricoUsuario(idUsuario);
    }

    public RelatorioElegibilidadeDTO gerarRelatorioElegibilidade(int dias) {
        return utilsRepository.gerarRelatorioElegibilidade(dias);
    }
}
