package dto;

public class InscritoDTO {

    private int idUsuario;
    private String nome;
    private String email;
    private String dataInscricao;

    public InscritoDTO(
            int idUsuario,
            String nome,
            String email,
            String dataInscricao
    ) {

        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.dataInscricao = dataInscricao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }
}