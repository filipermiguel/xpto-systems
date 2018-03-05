package com.xpto.cidades.dto;

public class CidadesPorEstadoDTO {

    private String estado;
    private Long id;

    public CidadesPorEstadoDTO() {
    }

    public CidadesPorEstadoDTO(String estado, Long id) {
        this.estado = estado;
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
