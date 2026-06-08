public void indexarCurso(Curso curso) {

    String texto =
        (curso.getNome() + " " + curso.getDescricao())
            .toLowerCase();

    String[] palavras = texto.split("\\W+");

    Map<String,Integer> contador = new HashMap<>();

    for(String p : palavras) {
        contador.put(p,
            contador.getOrDefault(p,0)+1);
    }

    for(Map.Entry<String,Integer> e : contador.entrySet()) {

        String termo = e.getKey();
        int tf = e.getValue();

        indiceInvertido
            .computeIfAbsent(
                termo,
                k -> new ArrayList<>()
            )
            .add(
                new Ocorrencia(
                    curso.getID(),
                    tf
                )
            );
    }
}