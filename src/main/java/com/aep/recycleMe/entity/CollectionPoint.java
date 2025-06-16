package com.aep.recycleMe.entity;



import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pontos_coleta")
public class CollectionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    @ElementCollection
    @CollectionTable(name = "ponto_materiais",
            joinColumns = @JoinColumn(name = "ponto_id"))
    @Column(name = "material")
    private List<String> materiais;

    @Column(nullable = false)
    private String endereco;

    private String telefone;

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String descricao;

    // Construtores, getters e setters
    public CollectionPoint() {}

    public CollectionPoint(String nome, String tipo, List<String> materiais,
                           String endereco, String telefone, String horarioFuncionamento,
                           Double latitude, Double longitude, String descricao) {
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getMateriais() { return materiais; }
    public void setMateriais(List<String> materiais) { this.materiais = materiais; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}