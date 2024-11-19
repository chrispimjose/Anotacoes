package model;

// Pacote: model
import java.util.Date;

public class Anotacao {
    private int id;
    private String titulo;
    private String descricao;
    private Date dataCriacao;
    private Date dataLembrete;
    private Date horaLembrete;

    // Construtor vazio
    public Anotacao() {
    }

    // Construtor completo
    public Anotacao(int id, String titulo, String descricao, Date dataCriacao, Date dataLembrete, Date horaLembrete) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataLembrete = dataLembrete;
        this.horaLembrete = horaLembrete;
    }

    // Getter e Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter para titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter e Setter para descricao
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Getter e Setter para dataCriacao
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // Getter e Setter para dataLembrete
    public Date getDataLembrete() {
        return dataLembrete;
    }

    public void setDataLembrete(Date dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    // Getter e Setter para horaLembrete
    public Date getHoraLembrete() {
        return horaLembrete;
    }

    public void setHoraLembrete(Date horaLembrete) {
        this.horaLembrete = horaLembrete;
    }

    @Override
    public String toString() {
        return "Anotacao{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataLembrete=" + dataLembrete +
                ", horaLembrete=" + horaLembrete +
                '}';
    }
}

