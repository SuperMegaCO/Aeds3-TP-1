package TempDataManager;

import java.time.LocalDate;

public class InscritoTempData {
    private int idUsuario;
    private String nome;
    private String email;
    private LocalDate dataInscricao;

    public InscritoTempData(int idUsuario, String nome, String email, LocalDate dataInscricao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.dataInscricao = dataInscricao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
