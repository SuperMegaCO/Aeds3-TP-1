package CRUD_RelacionamentoCursoUsuario;

import java.util.ArrayList;
import aed3.ArvoreBMais;
import CRUD_Curso.ArquivoCurso;
import CRUD_Curso.Curso;

public class ArquivoRelacionamentoCursoUsuario extends aed3.Arquivo<RelacionamentoCursoUsuario> {

    ArvoreBMais<ParIdUsuario_IdCurso> indiceIdUsuario;
    ArvoreBMais<ParIdCurso_IdUsuario> indiceIdCurso;

    public ArquivoRelacionamentoCursoUsuario() throws Exception {
        super("RelacionamentoCursoUsuario", RelacionamentoCursoUsuario.class.getConstructor());
        indiceIdUsuario = new ArvoreBMais<ParIdUsuario_IdCurso>(
                ParIdUsuario_IdCurso.class.getConstructor(),
                4,
                ".\\dados\\RelacionamentoCursoUsuario\\indiceUsuario.d.db" // diretório

        );
        indiceIdCurso = new ArvoreBMais<ParIdCurso_IdUsuario>(
                ParIdCurso_IdUsuario.class.getConstructor(),
                4,
                ".\\dados\\RelacionamentoCursoUsuario\\indiceCurso.d.db"// diretório
        );
    }

    // =========================================================================================
    // MÉTODOS ORIGINAIS (USANDO INTEIROS PARA BUSCAR O ID DO CURSO)
    // =========================================================================================

    public ArrayList<RelacionamentoCursoUsuario> readCursosDoUsuario(int idUsuario) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> relacionamentos = new ArrayList<>();
        ArrayList<ParIdUsuario_IdCurso> pares = indiceIdUsuario.read(new ParIdUsuario_IdCurso(idUsuario, -1, -1, ""));

        for (ParIdUsuario_IdCurso p : pares) {
            RelacionamentoCursoUsuario c = super.read(p.getIdRelacionamento());
            if (c != null) {
                relacionamentos.add(c);
            }
        }
        return relacionamentos;
    }

    public ArrayList<RelacionamentoCursoUsuario> readUsuariosDoCurso(int idCurso) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> relacionamentos = new ArrayList<>();
        ArrayList<ParIdCurso_IdUsuario> pares = indiceIdCurso.read(new ParIdCurso_IdUsuario(idCurso, -1, -1, ""));

        for (ParIdCurso_IdUsuario p : pares) {
            RelacionamentoCursoUsuario c = super.read(p.getIdRelacionamento());
            if (c != null) {
                relacionamentos.add(c);
            }
        }
        return relacionamentos;
    }

    public boolean delete(int idUsuario, int idCurso) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> inscricoes = this.readCursosDoUsuario(idUsuario);
        for (RelacionamentoCursoUsuario inscricao : inscricoes) {
            if (inscricao.getIdCurso() == idCurso) {
                return this.delete(inscricao.getId()); // Usa o delete(int id) herdado
            }
        }
        return false;
    }

    // =========================================================================================
    // SOBRECARGAS (CONVENIÊNCIA) PARA SUPORTAR CÓDIGO DO CURSO (String)
    // Traduzem o código do curso para ID antes de chamar os métodos reais.
    // =========================================================================================

    public ArrayList<RelacionamentoCursoUsuario> readUsuariosDoCurso(String codigoCurso) throws Exception {
        ArquivoCurso arquivoCurso = new ArquivoCurso();
        Curso curso = arquivoCurso.read(codigoCurso);
        if (curso == null) {
            return new ArrayList<>(); // Curso não encontrado
        }
        return readUsuariosDoCurso(curso.getId());
    }

    public boolean delete(int idUsuario, String codigoCurso) throws Exception {
        ArquivoCurso arquivoCurso = new ArquivoCurso();
        Curso curso = arquivoCurso.read(codigoCurso);
        if (curso == null) {
            return false;
        }
        return delete(idUsuario, curso.getId());
    }

    // =========================================================================================
    // OVERRIDES DE CRUD (MANIPULANDO OS ÍNDICES B+)
    // =========================================================================================

    @Override
    public int create(RelacionamentoCursoUsuario c) throws Exception {
        int id = super.create(c);
        c.setId(id); // Garante que o ID do relacionamento está configurado

        // Inclui o id (idRelacionamento) nos índices B+ para possibilitar buscas
        // posteriores
        indiceIdUsuario.create(new ParIdUsuario_IdCurso(c.getIdUsuario(), c.getIdCurso(), id, c.getNomeUsuario()));
        indiceIdCurso.create(new ParIdCurso_IdUsuario(c.getIdCurso(), c.getIdUsuario(), id, c.getNomeCurso()));
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        RelacionamentoCursoUsuario c = super.read(id);

        if (c != null) {
            if (super.delete(id)) {
                indiceIdUsuario.delete(new ParIdUsuario_IdCurso(c.getIdUsuario(), c.getIdCurso(), id, ""));
                indiceIdCurso.delete(new ParIdCurso_IdUsuario(c.getIdCurso(), c.getIdUsuario(), id, c.getNomeCurso()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(RelacionamentoCursoUsuario novoRelacionamento) throws Exception {
        RelacionamentoCursoUsuario velhoRelacionamento = super.read(novoRelacionamento.getId());

        if (velhoRelacionamento != null && super.update(novoRelacionamento)) {
            if (novoRelacionamento.getIdUsuario() != velhoRelacionamento.getIdUsuario() ||
                    novoRelacionamento.getIdCurso() != velhoRelacionamento.getIdCurso()) {

                // Remover usando as chaves antigas e o ID do relacionamento
                indiceIdUsuario.delete(new ParIdUsuario_IdCurso(velhoRelacionamento.getIdUsuario(),
                        velhoRelacionamento.getIdCurso(), velhoRelacionamento.getId(), ""));
                indiceIdCurso.delete(new ParIdCurso_IdUsuario(velhoRelacionamento.getIdCurso(),
                        velhoRelacionamento.getIdUsuario(), velhoRelacionamento.getId(), ""));

                // Inserir usando as chaves novas e o ID do relacionamento
                indiceIdUsuario.create(new ParIdUsuario_IdCurso(novoRelacionamento.getIdUsuario(),
                        novoRelacionamento.getIdCurso(), novoRelacionamento.getId(),
                        novoRelacionamento.getNomeUsuario()));
                indiceIdCurso.create(new ParIdCurso_IdUsuario(novoRelacionamento.getIdCurso(),
                        novoRelacionamento.getIdUsuario(), novoRelacionamento.getId(),
                        novoRelacionamento.getNomeCurso()));
            }
            return true;
        }
        return false;
    }

}
