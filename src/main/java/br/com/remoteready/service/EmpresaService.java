package br.com.remoteready.service;

import br.com.remoteready.model.Empresa;
import br.com.remoteready.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa atualizar(Long id, Empresa dados) {
        Empresa existente = buscarPorId(id);
        existente.setNomeEmpresa(dados.getNomeEmpresa());
        existente.setDescricaoEmpresa(dados.getDescricaoEmpresa());
        existente.setArea(dados.getArea());
        existente.setHiringNow(dados.getHiringNow());
        existente.setLogoUrl(dados.getLogoUrl());
        existente.setWebsite(dados.getWebsite());
        existente.setDataCriacao(dados.getDataCriacao());
        return empresaRepository.save(existente);
    }

    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada: " + id));
    }

    @Transactional
    public void excluir(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new IllegalArgumentException("Empresa não encontrada: " + id);
        }
        empresaRepository.deleteById(id);
    }
}