package CRUD_Curso;

import java.util.ArrayList;

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
                ".\\dados\\Cursos\\indiceCodigo.c.db");

        indiceUsuario = new ArvoreBMais<>(
                ParIdUsuarioIdCurso.class.getConstructor(),
                5,
                ".\\dados\\Cursos\\indiceUsuario.db");
    }

    public ArrayList<Curso> readCursosDoUsuario(int idUsuario) throws Exception {
        ArrayList<Curso> cursos = new ArrayList<>();
        ArrayList<ParIdUsuarioIdCurso> pares = indiceUsuario.read(new ParIdUsuarioIdCurso(idUsuario, "", -1));

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

        // gera código automaticamente ou limpa espaços
        if (c.getCodigo() == null || c.getCodigo().isEmpty()) {
            c.setCodigo(GeradorCodigo.gerar());
        } else {
            c.setCodigo(c.getCodigo().trim());
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
        String codigoTrimmed = codigo.trim();
        
        // Tenta buscar via índice primeiro
        ParCodigoID pci = indiceCodigo.read(ParCodigoID.hash(codigoTrimmed));
        if (pci != null) {
            return read(pci.getId());
        }
        
        // Fallback: busca linear se o índice não encontrar
        // Isso garante que funciona mesmo se o índice estiver vazio/inconsistente
        int id = 1;
        while (true) {
            Curso c = super.read(id);
            if (c == null) {
                break;
            }
            if (c.getCodigo().trim().equals(codigoTrimmed)) {
                return c;
            }
            id++;
        }
        
        return null;
    }

    // ========================
    // DELETE POR CÓDIGO
    // ========================
    public boolean delete(String codigo) throws Exception {
        ParCodigoID pci = indiceCodigo.read(ParCodigoID.hash(codigo.trim()));

        if (pci != null)
            if (delete(pci.getId()))
                return indiceCodigo.delete(ParCodigoID.hash(codigo.trim()));

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
                indiceCodigo.delete(ParCodigoID.hash(c.getCodigo().trim()));

                // remove índice usuário
                indiceUsuario.delete(
                        new ParIdUsuarioIdCurso(c.getIdUsuario(), c.getNome(), id));

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
                        new ParIdUsuarioIdCurso(antigo.getIdUsuario(), antigo.getNome(), antigo.getId()));

                indiceUsuario.create(
                        new ParIdUsuarioIdCurso(novo.getIdUsuario(), novo.getNome(), novo.getId()));
            }

            return true;
        }

        return false;
    }

    // ========================
    // READ ALL CURSOS ORDENADOS POR DATA
    // ========================
    public ArrayList<Curso> readAllCursosOrdenadosPorData() throws Exception {
        ArrayList<Curso> cursos = new ArrayList<>();
        // Ler todos os cursos iterando de 1 até encontrar um null
        int id = 1;
        while (true) {
            Curso c = super.read(id);
            if (c == null) {
                break;
            }
            cursos.add(c);
            id++;
        }
        // Ordenar por data de início (usando a data de início)
        cursos.sort((c1, c2) -> c1.getDataInicio().compareTo(c2.getDataInicio()));
        return cursos;
    }

    // ========================
    // READ CURSOS PAGINADOS
    // ========================
    public ArrayList<Curso> readCursosPaginados(int pagina, int itensPorPagina) throws Exception {
        ArrayList<Curso> todosCursos = readAllCursosOrdenadosPorData();
        ArrayList<Curso> paginaCursos = new ArrayList<>();
        
        int inicio = (pagina - 1) * itensPorPagina;
        int fim = Math.min(inicio + itensPorPagina, todosCursos.size());
        
        for (int i = inicio; i < fim; i++) {
            paginaCursos.add(todosCursos.get(i));
        }
        
        return paginaCursos;
    }

    // ========================
    // CONTAR TOTAL DE CURSOS
    // ========================
    public int contarTotalCursos() throws Exception {
        ArrayList<Curso> todosCursos = readAllCursosOrdenadosPorData();
        return todosCursos.size();
    }
}
