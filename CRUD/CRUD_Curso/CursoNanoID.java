import java.time.LocalDate;

public class CursoNanoID {

    private String nanoID;
    private String nome;
    private LocalDate dataInicio;

    public CursoNanoID() {}

    public CursoNanoID(String nanoID, String nome, LocalDate dataInicio) {
        this.nanoID = nanoID;
        this.nome = nome;
        this.dataInicio = dataInicio;
    }

    public String getNanoID() {
        return nanoID;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + nanoID +
                "\nNome: " + nome +
                "\nData Início: " + dataInicio;
    }
}