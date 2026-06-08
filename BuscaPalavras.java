public List<ResultadoBusca>
buscar(String consulta) {

    Map<Integer,Double> ranking =
        new HashMap<>();

    String[] termos =
        consulta.toLowerCase().split("\\W+");

    for(String termo : termos) {

        List<Ocorrencia> lista =
            indiceInvertido.get(termo);

        if(lista == null)
            continue;

        double idf = calcularIDF(termo);

        for(Ocorrencia o : lista) {

            double tfidf =
                o.getFrequencia() * idf;

            ranking.merge(
                o.getIdCurso(),
                tfidf,
                Double::sum
            );
        }
    }

    return ranking.entrySet()
            .stream()
            .sorted(
                (a,b) -> Double.compare(
                    b.getValue(),
                    a.getValue()
                )
            )
            .map(e ->
                new ResultadoBusca(
                    e.getKey(),
                    e.getValue()
                )
            )
            .toList();
}