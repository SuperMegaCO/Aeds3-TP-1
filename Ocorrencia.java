public class Ocorrencia {

    private int idCurso;
    private int frequencia;

    public Ocorrencia(int idCurso) {
        this.idCurso = idCurso;
        this.frequencia = 1;
    }

    public void incrementar() {
        frequencia++;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public int getFrequencia() {
        return frequencia;
    }
}