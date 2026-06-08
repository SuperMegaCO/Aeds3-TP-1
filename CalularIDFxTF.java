double calcularIDF(String termo) {

    int N = totalCursos;

    List<Ocorrencia> lista =
        indiceInvertido.get(termo);

    if(lista == null)
        return 0;

    int df = lista.size();

    return Math.log((double) N / df);

double score =
    ocorrencia.getFrequencia()
    * calcularIDF(termo);
}