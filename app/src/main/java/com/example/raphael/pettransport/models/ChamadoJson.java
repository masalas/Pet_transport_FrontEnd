package com.example.raphael.pettransport.models;

import java.io.Serializable;
import java.util.List;

public class ChamadoJson implements Serializable {

    private Long id;
    private Long pet;
    private Long motorista;
    private Double latitude;
    private Double longitude;
    private EstadoChamado estadoChamado;
    private List<TipoServico> tiposServicos;

    public Long id() {
        return id;
    }

    public ChamadoJson id(Long id) {
        this.id = id;
        return this;
    }

    public Long pet() {
        return pet;
    }

    public ChamadoJson pet(Long pet) {
        this.pet = pet;
        return this;
    }

    public Long motorista() {
        return motorista;
    }

    public ChamadoJson motorista(Long motorista) {
        this.motorista = motorista;
        return this;
    }

    public Double latitude() {
        return latitude;
    }

    public ChamadoJson latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double longitude() {
        return longitude;
    }

    public ChamadoJson longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public EstadoChamado estadoChamado() {
        return estadoChamado;
    }

    public ChamadoJson estadoChamado(EstadoChamado estadoChamado) {
        this.estadoChamado = estadoChamado;
        return this;
    }

    public List<TipoServico> tiposServicos() {
        return tiposServicos;
    }

    public ChamadoJson tiposServicos(List<TipoServico> tipoServicos) {
        this.tiposServicos = tipoServicos;
        return this;
    }
}
