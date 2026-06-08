public class ResultadoBuscas {

    private int idCurso;
    private double score;

    public ResultadoBusca(
            int idCurso,
            double score) {

        this.idCurso = idCurso;
        this.score = score;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public double getScore() {
        return score;
    }
}