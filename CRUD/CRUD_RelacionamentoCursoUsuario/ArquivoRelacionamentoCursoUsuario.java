package CRUD_RelacionamentoCursoUsuario;

import java.util.ArrayList;
import aed3.ArvoreBMais;
import CRUD_Curso.*;

public class ArquivoRelacionamentoCursoUsuario extends aed3.Arquivo<RelacionamentoCursoUsuario> {

    ArvoreBMais<ParIdUsuario_IdCurso> indiceIdUsuario;
    ArvoreBMais<ParIdCurso_IdUsuario> indiceIdCurso;

    public ArrayList<RelacionamentoCursoUsuario> readCursosDoUsuario(int idUsuario) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> relacionamentos = new ArrayList<>();
        ArrayList<ParIdUsuario_IdCurso> pares = indiceIdUsuario.read(new ParIdUsuario_IdCurso(idUsuario, "", ""));

        for (ParIdUsuario_IdCurso p : pares) {
            RelacionamentoCursoUsuario c = new Rel);
            if (c != null) {
                relacionamentos.add(c);
            }
        }
        return relacionamentos;
    }

    public ArrayList<RelacionamentoCursoUsuario> readUsuariosDoCurso(int idCurso) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> relacionamentos = new ArrayList<>();
        ArrayList<ParIdCurso_IdUsuario> pares = indiceIdCurso.read(new ParIdCurso_IdUsuario(idCurso, -1, ""));

        for (ParIdCurso_IdUsuario p : pares) {
            RelacionamentoCursoUsuario c = super.read(p.getIdUsuario());
            if (c != null) {
                relacionamentos.add(c);
            }
        }
        return relacionamentos;
    }

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

    @Override
    public int create(RelacionamentoCursoUsuario c) throws Exception {
        int id = super.create(c);
        indiceIdUsuario.create(new ParIdUsuario_IdCurso(c.getIdUsuario(), c.getIdCurso(), c.getNomeUsuario()));
        indiceIdCurso.create(new ParIdCurso_IdUsuario(c.getIdCurso(), c.getIdUsuario(), c.getNomeCurso()));
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        RelacionamentoCursoUsuario c = super.read(id);

        if (c != null) {
            if (super.delete(id)) {
                indiceIdUsuario.delete(new ParIdUsuario_IdCurso(c.getIdUsuario(), c.getIdCurso(), ""));
                indiceIdCurso.delete(new ParIdCurso_IdUsuario(c.getIdCurso(), c.getIdUsuario(), c.getNomeCurso()));
                return true;
            }
        }
        return false;
    }

    public boolean delete(int idUsuario, int idCurso) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> inscricoes = this.readCursosDoUsuario(idUsuario);
        for (RelacionamentoCursoUsuario inscricao : inscricoes) {
            if (inscricao.getIdCurso() == idCurso) {
                return this.delete(inscricao.getId());
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

                indiceIdUsuario.delete(new ParIdUsuario_IdCurso(velhoRelacionamento.getIdUsuario(),
                        velhoRelacionamento.getIdCurso(), ""));
                indiceIdCurso.delete(new ParIdCurso_IdUsuario(velhoRelacionamento.getIdCurso(),
                        velhoRelacionamento.getIdUsuario(), ""));

                indiceIdUsuario.create(new ParIdUsuario_IdCurso(novoRelacionamento.getIdUsuario(),
                        novoRelacionamento.getIdCurso(), novoRelacionamento.getNomeUsuario()));
                indiceIdCurso.create(new ParIdCurso_IdUsuario(novoRelacionamento.getIdCurso(),
                        novoRelacionamento.getIdUsuario(), novoRelacionamento.getNomeCurso()));
            }
            return true;
        }
        return false;
    }

}
