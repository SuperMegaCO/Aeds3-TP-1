package CRUD_Curso;

import aed3.*;

public class ArquivoCurso extends aed3.Arquivo<Curso> {

    HashExtensivel<ParCodigoID> indiceCodigo;
    HashExtensivel<ParIdUsuarioIdCurso> indiceUsuario;

    public ArquivoCurso() throws Exception {
        super("Cursos", Curso.class.getConstructor());

        indiceCodigo = new HashExtensivel<>(
            ParCodigoID.class.getConstructor(),
            4,
            ".\\dados\\Cursos\\indiceCodigo.d.db",
            ".\\dados\\Cursos\\indiceCodigo.c.db"
        );

        indiceUsuario = new HashExtensivel<>(
            ParIdUsuarioIdCurso.class.getConstructor(),
            4,
            ".\\dados\\Cursos\\indiceUsuario.d.db",
            ".\\dados\\Cursos\\indiceUsuario.c.db"
        );
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
        indiceUsuario.create(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));

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
                    ParIdUsuarioIdCurso.hash(c.getIdUsuario(), id)
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

            // se mudar usuário → atualiza índice
            if (novo.getIdUsuario() != antigo.getIdUsuario()) {

                indiceUsuario.delete(
                    ParIdUsuarioIdCurso.hash(antigo.getIdUsuario(), antigo.getId())
                );

                indiceUsuario.create(
                    new ParIdUsuarioIdCurso(novo.getIdUsuario(), novo.getId())
                );
            }

            return true;
        }

        return false;
    }
}
