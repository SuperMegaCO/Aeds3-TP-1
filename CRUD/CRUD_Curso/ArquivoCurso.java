import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArquivoCurso {

    private List<Curso> cursos;

    public ArquivoCurso() {
        cursos = new ArrayList<>();
    }

    // Apenas para testes
    public void adicionar(Curso c) {
        cursos.add(c);
    }

    /*
     * Buscar curso pelo NanoID
     */
    public Curso buscarPorCodigo(String nanoID) {

        for (Curso c : cursos) {

            if (c.getNanoID().equalsIgnoreCase(nanoID)) {
                return c;
            }
        }

        return null;
    }

    /*
     * Retorna uma página de cursos
     * 10 elementos por página
     */
    public List<Curso> listarPaginado(int pagina) {

        // Ordena pela data de início
        cursos.sort(Comparator.comparing(Curso::getDataInicio));

        int elementosPorPagina = 10;

        int inicio = (pagina - 1) * elementosPorPagina;
        int fim = Math.min(inicio + elementosPorPagina, cursos.size());

        if (inicio >= cursos.size() || inicio < 0) {
            return new ArrayList<>();
        }

        return cursos.subList(inicio, fim);
    }

    /*
     * Quantidade total de páginas
     */
    public int totalPaginas() {

        int elementosPorPagina = 10;

        return (int) Math.ceil((double) cursos.size() / elementosPorPagina);
    }
}