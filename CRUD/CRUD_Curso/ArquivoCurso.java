package CRUD_Curso;

import aed3.*;

public class ArquivoCurso extends aed3.Arquivo<Curso> {

    HashExtensivel<ParCodigoID> indiceCodigo;
ArvoreBMais<ParIdUsuarioIdCurso> indiceUsuario;
    public ArquivoCurso() throws Exception {
        super("Cursos", Curso.class.getConstructor());

        indiceCodigo = new HashExtensivel<>(
            ParCodigoID.class.getConstructor(),
            4,
            ".\\dados\\Cursos\\indiceCodigo.d.db",
            ".\\dados\\Cursos\\indiceCodigo.c.db"
        );

        indiceUsuario = new ArvoreBMais<>(
    ParIdUsuarioIdCurso.class.getConstructor(),
    5,
    ".\\dados\\Cursos\\indiceUsuario.db"
);
    }

    public java.util.ArrayList<Curso> readCursosDoUsuario(int idUsuario) throws Exception {
        java.util.ArrayList<Curso> cursos = new java.util.ArrayList<>();
        java.util.ArrayList<ParIdUsuarioIdCurso> pares = indiceUsuario.read(new ParIdUsuarioIdCurso(idUsuario, "", -1));
        
        for (ParIdUsuarioIdCurso p : pares) {
            Curso c = super.read(p.getIdCurso());
            if (c != null) {
                cursos.add(c);
            }
        }
        return cursos;
    }
    // ========================
    // CREATE
    // ========================
    @Override
    public int create(Curso c) throws Exception {

        // gera código automaticamente
        if (c.getCodigo() == null || c.getCodigo().isEmpty()) {
            c.setCodigo(GeradorCodigo.gerar());
        }

        int id = super.create(c);

        // índice por código (único)
        indiceCodigo.create(new ParCodigoID(c.getCodigo(), id));

        // índice por usuário (1:N)
        indiceUsuario.create(new ParIdUsuarioIdCurso(c.getIdUsuario(), c.getNome(), id));
        return id;
    }

    // ========================
    // READ POR CÓDIGO
    // ========================
    public Curso read(String codigo) throws Exception {
        ParCodigoID pci = indiceCodigo.read(ParCodigoID.hash(codigo));

        if (pci == null)
            return null;

        return read(pci.getId());
    }

    // ========================
    // DELETE POR CÓDIGO
    // ========================
    public boolean delete(String codigo) throws Exception {
        ParCodigoID pci = indiceCodigo.read(ParCodigoID.hash(codigo));

        if (pci != null)
            if (delete(pci.getId()))
                return indiceCodigo.delete(ParCodigoID.hash(codigo));

        return false;
    }

    // ========================
    // DELETE POR ID
    // ========================
    @Override
    public boolean delete(int id) throws Exception {

        Curso c = super.read(id);

        if (c != null) {
            if (super.delete(id)) {

                // remove índice código
                indiceCodigo.delete(ParCodigoID.hash(c.getCodigo()));

                // remove índice usuário
                indiceUsuario.delete(
    new ParIdUsuarioIdCurso(c.getIdUsuario(), c.getNome(), id)
);

                return true;
            }
        }

        return false;
    }

    // ========================
    // UPDATE
    // ========================
    @Override
    public boolean update(Curso novo) throws Exception {

        Curso antigo = read(novo.getId());

        if (antigo == null)
            return false;

        if (super.update(novo)) {

            // código NÃO deve mudar (segurança)
            novo.setCodigo(antigo.getCodigo());

            // se mudar usuário ou nome → atualiza índice na árvore B+
            if (novo.getIdUsuario() != antigo.getIdUsuario() || !novo.getNome().equals(antigo.getNome())) {

                indiceUsuario.delete(
                    new ParIdUsuarioIdCurso(antigo.getIdUsuario(), antigo.getNome(), antigo.getId())
                );

                indiceUsuario.create(
                    new ParIdUsuarioIdCurso(novo.getIdUsuario(), novo.getNome(), novo.getId())
                );
            }

            return true;
        }

        return false;
    }
}
