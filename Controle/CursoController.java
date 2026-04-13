package Controle;
import Curso;
public class CursoController {

    private CursoCRUD crud;

    public CursoController() throws Exception {
        crud = new CursoCRUD("cursos.db");
    }

    public void criarCurso(int idUsuario, String nome, String data,
                           String descricao, int estado) throws Exception {

        String codigo = GeradorCodigo.gerar();

        Curso c = new Curso(idUsuario, nome, data, descricao, codigo, estado);

        int id = crud.create(c);

        System.out.println("Curso criado com ID: " + id);
    }

    public void buscarCurso(int id) throws Exception {
        Curso c = crud.read(id);

        if (c != null)
            System.out.println(c);
        else
            System.out.println("Curso não encontrado.");
    }

    public void atualizarCurso(Curso c) throws Exception {
        if (crud.update(c))
            System.out.println("Curso atualizado!");
        else
            System.out.println("Erro ao atualizar.");
    }

    public void deletarCurso(int id) throws Exception {
        if (crud.delete(id))
            System.out.println("Curso removido!");
        else
            System.out.println("Erro ao remover.");
    }

    public void listarCursosUsuario(int idUsuario) throws Exception {
        crud.listarPorUsuario(idUsuario);
    }
}