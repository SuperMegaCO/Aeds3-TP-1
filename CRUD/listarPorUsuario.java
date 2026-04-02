public void listarPorUsuario(int idUsuario) throws Exception {
    arq.seek(4);

    while (arq.getFilePointer() < arq.length()) {
        byte lapide = arq.readByte();
        int tamanho = arq.readInt();

        byte[] ba = new byte[tamanho];
        arq.read(ba);

        if (lapide == ' ') {
            Curso c = new Curso();
            c.fromByteArray(ba);

            if (c.getIdUsuario() == idUsuario) {
                System.out.println(c);
            }
        }
    }
}