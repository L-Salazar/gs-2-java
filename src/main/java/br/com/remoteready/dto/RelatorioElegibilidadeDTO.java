package br.com.remoteready.dto;

import java.time.LocalDate;
import java.util.List;

public class RelatorioElegibilidadeDTO {

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<UsuarioAptoDTO> usuariosAptos;
    private List<UsuarioNaoAptoDTO> usuariosNaoAptos;

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<UsuarioAptoDTO> getUsuariosAptos() {
        return usuariosAptos;
    }

    public void setUsuariosAptos(List<UsuarioAptoDTO> usuariosAptos) {
        this.usuariosAptos = usuariosAptos;
    }

    public List<UsuarioNaoAptoDTO> getUsuariosNaoAptos() {
        return usuariosNaoAptos;
    }

    public void setUsuariosNaoAptos(List<UsuarioNaoAptoDTO> usuariosNaoAptos) {
        this.usuariosNaoAptos = usuariosNaoAptos;
    }
}
