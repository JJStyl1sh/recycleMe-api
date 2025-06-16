package com.aep.recycleMe.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.util.List;

public class CollectionPointDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @NotNull(message = "Lista de materiais é obrigatória")
    private List<String> materiais;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    private String telefone;

    private String horarioFuncionamento;

    @NotNull(message = "Latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude deve estar entre -90 e 90")
    @DecimalMax(value = "90.0", message = "Latitude deve estar entre -90 e 90")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "Longitude deve estar entre -180 e 180")
    @DecimalMax(value = "180.0", message = "Longitude deve estar entre -180 e 180")
    private Double longitude;

    private String descricao;

    // Construtores
    public CollectionPointDTO() {}

    public CollectionPointDTO(Long id, String nome, String tipo, List<String> materiais,
                              String endereco, String telefone, String horarioFuncionamento,
                              Double latitude, Double longitude, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.materiais = materiais;
        this.endereco = endereco;
        this.telefone = telefone;
        this.horarioFuncionamento = horarioFuncionamento;
        this.latitude = latitude;
        this.longitude = longitude;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<String> materiais) {
        this.materiais = materiais;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CollectionPointDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", materiais=" + materiais +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}