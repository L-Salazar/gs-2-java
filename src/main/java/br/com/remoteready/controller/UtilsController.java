package br.com.remoteready.controller;

import br.com.remoteready.dto.ChatHistoryResult;
import br.com.remoteready.dto.RelatorioElegibilidadeDTO;
import br.com.remoteready.service.UtilsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/utils")
public class UtilsController {

    private final UtilsService utilsService;

    public UtilsController(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    @GetMapping
    public String telaUtils(@RequestParam(value = "userId", required = false) Long userId,
                            @RequestParam(value = "dias", required = false) Integer dias,
                            Model model) {

        if (dias == null || dias <= 0) {
            dias = 30;
        }

        // Relatório sempre é carregado
        RelatorioElegibilidadeDTO relatorio = utilsService.gerarRelatorioElegibilidade(dias);
        model.addAttribute("relatorio", relatorio);
        model.addAttribute("dias", dias);

        // Histórico é carregado apenas se userId informado
        if (userId != null) {
            Optional<ChatHistoryResult> historicoOpt = utilsService.obterHistoricoUsuario(userId);
            if (historicoOpt.isPresent()) {
                model.addAttribute("historico", historicoOpt.get());
            } else {
                model.addAttribute("historicoErro", "Usuário não encontrado para o ID " + userId);
            }
        }

        model.addAttribute("userId", userId);

        return "home/utils";
    }

    // Caso você prefira POST para o histórico:
    @PostMapping("/historico")
    public String buscarHistorico(@RequestParam("userId") Long userId,
                                  @RequestParam(value = "dias", required = false) Integer dias,
                                  Model model) {
        // Redireciona para GET mantendo parâmetros na URL
        if (dias == null || dias <= 0) {
            dias = 30;
        }
        return "redirect:/utils?userId=" + userId + "&dias=" + dias;
    }

    @PostMapping("/relatorio")
    public String atualizarRelatorio(@RequestParam("dias") Integer dias,
                                     @RequestParam(value = "userId", required = false) Long userId) {
        if (dias == null || dias <= 0) {
            dias = 30;
        }
        String url = "/utils?dias=" + dias;
        if (userId != null) {
            url += "&userId=" + userId;
        }
        return "redirect:" + url;
    }
}
