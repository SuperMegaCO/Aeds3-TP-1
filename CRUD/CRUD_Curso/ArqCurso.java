import java.io.*;

public class ArqCurso {

    private RandomAccessFile arq;

    public CursoCRUD(String nomeArquivo) throws IOException {
        arq = new RandomAccessFile(nomeArquivo, "rw");

        // Cabeçalho: último ID
        if (arq.length() == 0) {
            arq.writeInt(0);
        }
    }

    // ========================
    // CREATE
    // ========================
    public int create(Curso c) throws Exception {

        arq.seek(0);
        int ultimoID = arq.readInt();
        ultimoID++;

        arq.seek(0);
        arq.writeInt(ultimoID);

        c.setId(ultimoID);

        // gera código automaticamente
        if (c.getCodigo() == null || c.getCodigo().isEmpty()) {
            c.setCodigo(GeradorCodigo.gerar());
        }

        arq.seek(arq.length());

        byte[] ba = c.toByteArray();

        arq.writeByte(' '); // lápide válida
        arq.writeInt(ba.length);
        arq.write(ba);

        return c.getId();
    }

    // ========================
    // READ POR ID
    // ========================
    public Curso read(int id) throws Exception {

        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            int tamanho = arq.readInt();

            byte[] ba = new byte[tamanho];
            arq.read(ba);

            if (lapide == ' ') {
                Curso c = new Curso();
                c.fromByteArray(ba);

                if (c.getId() == id) {
                    return c;
                }
            }
        }

        return null;
    }

    // ========================
    // READ POR CÓDIGO (IMPORTANTE)
    // ========================
    public Curso readByCodigo(String codigo) throws Exception {

        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            int tamanho = arq.readInt();

            byte[] ba = new byte[tamanho];
            arq.read(ba);

            if (lapide == ' ') {
                Curso c = new Curso();
                c.fromByteArray(ba);

                if (c.getCodigo().equals(codigo)) {
                    return c;
                }
            }
        }

        return null;
    }

    // ========================
    // UPDATE
    // ========================
    public boolean update(Curso novo) throws Exception {

        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {

            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            int tamanho = arq.readInt();

            byte[] ba = new byte[tamanho];
            arq.read(ba);

            if (lapide == ' ') {

                Curso atual = new Curso();
                atual.fromByteArray(ba);

                if (atual.getId() == novo.getId()) {

                    // preserva dados importantes
                    novo.setCodigo(atual.getCodigo());
                    novo.setIdUsuario(atual.getIdUsuario());

                    byte[] novoBa = novo.toByteArray();

                    if (novoBa.length <= tamanho) {
                        arq.seek(pos + 1 + 4);
                        arq.write(novoBa);
                    } else {
                        // marca como removido
                        arq.seek(pos);
                        arq.writeByte('*');

                        // grava no final
                        arq.seek(arq.length());
                        arq.writeByte(' ');
                        arq.writeInt(novoBa.length);
                        arq.write(novoBa);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    // ========================
    // DELETE
    // ========================
    public boolean delete(int id) throws Exception {

        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {

            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            int tamanho = arq.readInt();

            byte[] ba = new byte[tamanho];
            arq.read(ba);

            if (lapide == ' ') {

                Curso c = new Curso();
                c.fromByteArray(ba);

                if (c.getId() == id) {
                    arq.seek(pos);
                    arq.writeByte('*');
                    return true;
                }
            }
        }

        return false;
    }

    // ========================
    // LISTAR CURSOS DE UM USUÁRIO (1:N)
    // ========================
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

    // ========================
    // LISTAR TODOS (debug)
    // ========================
    public void listarTodos() throws Exception {

        arq.seek(4);

        while (arq.getFilePointer() < arq.length()) {

            byte lapide = arq.readByte();
            int tamanho = arq.readInt();

            byte[] ba = new byte[tamanho];
            arq.read(ba);

            if (lapide == ' ') {
                Curso c = new Curso();
                c.fromByteArray(ba);
                System.out.println(c);
            }
        }
    }
}