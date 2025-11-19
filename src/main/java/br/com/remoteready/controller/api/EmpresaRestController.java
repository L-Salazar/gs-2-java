package br.com.remoteready.controller.api;

import br.com.remoteready.model.Empresa;
import br.com.remoteready.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EmpresaRestController {

    private final EmpresaService empresaService;

    public EmpresaRestController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // GET /api/companies - Listar todas
    @GetMapping
    public ResponseEntity<List<Empresa>> listar() {
        List<Empresa> empresas = empresaService.listar();
        return ResponseEntity.ok(empresas);
    }

    // GET /api/companies/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.buscarPorId(id);
            return ResponseEntity.ok(empresa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/companies - Criar nova
    @PostMapping
    public ResponseEntity<Empresa> criar(@RequestBody Empresa empresa) {
        try {
            Empresa novaEmpresa = empresaService.salvar(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/companies/{id} - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        try {
            Empresa empresaAtualizada = empresaService.atualizar(id, empresa);
            return ResponseEntity.ok(empresaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/companies/{id} - Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            empresaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/companies/hiring - Listar empresas que estão contratando
    @GetMapping("/hiring")
    public ResponseEntity<List<Empresa>> listarContratando() {
        List<Empresa> empresas = empresaService.listar()
                .stream()
                .filter(e -> "Y".equals(e.getHiringNow()))
                .toList();
        return ResponseEntity.ok(empresas);
    }
}