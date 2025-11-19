package br.com.remoteready.controller;

import br.com.remoteready.model.Empresa;
import br.com.remoteready.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Listagem
    @GetMapping
    public String listar(Model model) {
        List<Empresa> empresas = empresaService.listar();
        model.addAttribute("empresas", empresas);
        return "companies/listagem";
    }

    // Formulário - novo
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("hiringOptions", List.of("Y", "N"));
        return "companies/formulario";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute @Valid Empresa empresa,
                         org.springframework.validation.BindingResult binding,
                         Model model) {
        model.addAttribute("hiringOptions", List.of("Y", "N"));
        if (binding.hasErrors()) {
            return "companies/formulario";
        }
        empresaService.salvar(empresa);
        return "redirect:/companies";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id);
        model.addAttribute("empresa", empresa);
        model.addAttribute("hiringOptions", List.of("Y", "N"));
        return "companies/formulario";
    }

    // Atualizar
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute @Valid Empresa empresa,
                          org.springframework.validation.BindingResult binding,
                          Model model) {
        model.addAttribute("hiringOptions", List.of("Y", "N"));
        if (binding.hasErrors()) {
            return "companies/formulario";
        }
        empresaService.atualizar(id, empresa);
        return "redirect:/companies";
    }

    // Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        empresaService.excluir(id);
        return "redirect:/companies";
    }
}